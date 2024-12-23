package kr.teammangers.dev.memo.application.facade;

import kr.teammangers.dev.memo.application.service.MemoService;
import kr.teammangers.dev.memo.dto.MemoDto;
import kr.teammangers.dev.memo.dto.request.CreateMemoReq;
import kr.teammangers.dev.memo.dto.request.DeleteMemoReq;
import kr.teammangers.dev.memo.dto.request.FixMemoReq;
import kr.teammangers.dev.memo.dto.request.UpdateMemoReq;
import kr.teammangers.dev.memo.dto.response.*;
import kr.teammangers.dev.tag.application.service.MemoTagService;
import kr.teammangers.dev.tag.application.service.TagService;
import kr.teammangers.dev.tag.dto.TagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static kr.teammangers.dev.memo.mapper.MemoResMapper.MEMO_RES_MAPPER;
import static kr.teammangers.dev.tag.domain.enums.TagType.MEMO;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemoApiFacade {

    private final MemoService memoService;
    private final TagService tagService;
    private final MemoTagService memoTagService;

    @Transactional
    public CreateMemoRes createMemo(CreateMemoReq req) {
        MemoDto memoDto = memoService.save(req);
        Optional.ofNullable(req.memoTagList())
                .ifPresent(memoTagList -> memoTagList
                        .forEach(tagName -> saveMemoTagFromTagName(memoDto.id(), tagName)));

        return MEMO_RES_MAPPER.toCreate(memoDto);
    }

    public List<GetMemoRes> getMemoList(Long folderId, Boolean isFixed) {
        List<MemoDto> memoDtoList = memoService.findAllDtoByFolderId(folderId, isFixed);
        return memoDtoList.stream()
                .map(memoDto -> {
                    List<TagDto> tagDtoList = memoTagService.findAllTagDtoByMemoId(memoDto.id());
                    return MEMO_RES_MAPPER.toGet(memoDto, tagDtoList);
                }).toList();
    }

    public List<GetMemoRes> getMemoListByFixed(Long teamId) {
        List<MemoDto> memoDtoList =  memoService.findAllDtoByFixed(teamId);
        return memoDtoList.stream()
                .map(memoDto -> {
                    List<TagDto> tagDtoList = memoTagService.findAllTagDtoByMemoId(memoDto.id());
                    return MEMO_RES_MAPPER.toGet(memoDto, tagDtoList);
                }).toList();
    }

    @Transactional
    public UpdateMemoRes updateMemo(Long memberId, UpdateMemoReq req) {
        memoService.validateMemoAdmin(req.memoId(), memberId);
        MemoDto memoDto = memoService.update(req);

        List<String> existingTagNames = memoTagService.findAllTagDtoByMemoId(req.memoId()).stream()
                .map(TagDto::name).toList();

        Optional.ofNullable(req.memoTagList())
                .ifPresentOrElse(requestTagNames -> {
                    List<String> tagsToAdd = requestTagNames.stream()
                            .filter(tagName -> !existingTagNames.contains(tagName))
                            .toList();

                    List<String> tagsToRemove = existingTagNames.stream()
                            .filter(tagName -> !req.memoTagList().contains(tagName))
                            .toList();

                    tagsToAdd.forEach(tagName -> saveMemoTagFromTagName(memoDto.id(), tagName));
                    tagsToRemove.forEach(tagName -> memoTagService.deleteByMemoIdAndTagName(memoDto.id(), tagName));
                }, () -> memoTagService.deleteAllByMemoId(memoDto.id()));

        return MEMO_RES_MAPPER.toUpdate(memoDto);
    }

    @Transactional
    public DeleteMemoRes deleteMemo(Long memberId, DeleteMemoReq req) {
        memoService.validateMemoAdmin(req.memoId(), memberId);
        memoTagService.deleteAllByMemoId(req.memoId());
        memoService.deleteById(req.memoId());
        return MEMO_RES_MAPPER.toDelete(req.memoId());
    }

    @Transactional
    public FixMemoRes fixMemo(FixMemoReq req) {
        Boolean isFixed = memoService.updateFixStatus(req.memoId());
        return MEMO_RES_MAPPER.toFix(req.memoId(), isFixed);
    }

    private void saveMemoTagFromTagName(Long memoId, String tagName) {
        TagDto tagDto = tagService.findDtoOrSave(tagName, MEMO);
        memoTagService.save(memoId, tagDto.id());
    }

}
