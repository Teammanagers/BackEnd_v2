package kr.teammangers.dev.memo.application.facade;

import kr.teammangers.dev.memo.application.service.MemoService;
import kr.teammangers.dev.memo.dto.MemoDto;
import kr.teammangers.dev.memo.dto.request.CreateMemoReq;
import kr.teammangers.dev.memo.dto.request.UpdateMemoReq;
import kr.teammangers.dev.memo.dto.response.GetMemoRes;
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
    public MemoDto createMemo(Long folderId, Long teamId, CreateMemoReq req) {
        MemoDto memoDto = memoService.save(folderId, teamId, req);
        Optional.ofNullable(req.memoTagList())
                .ifPresent(memoTagList -> memoTagList
                        .forEach(tagName -> saveMemoTagFromTagName(memoDto.id(), tagName)));
        return memoDto;
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
    public MemoDto updateMemo(Long memberId, Long memoId, UpdateMemoReq req) {
        memoService.validateMemoAdmin(memoId, memberId);
        MemoDto memoDto = memoService.update(memoId, req);

        List<String> existingTagNames = memoTagService.findAllTagDtoByMemoId(memoId).stream()
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

        return memoDto;
    }

    @Transactional
    public Long deleteMemo(Long memberId, Long memoId) {
        memoService.validateMemoAdmin(memoId, memberId);
        memoTagService.deleteAllByMemoId(memoId);
        memoService.deleteById(memoId);
        return memoId;
    }

    @Transactional
    public MemoDto fixMemo(Long memoId) {
        return memoService.updateFixStatus(memoId);
    }

    private void saveMemoTagFromTagName(Long memoId, String tagName) {
        TagDto tagDto = tagService.findDtoOrSave(tagName, MEMO);
        memoTagService.save(memoId, tagDto.id());
    }

}
