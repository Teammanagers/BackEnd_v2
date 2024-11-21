package kr.teammangers.dev.memo.application.impl;

import kr.teammangers.dev.memo.application.MemoCrudService;
import kr.teammangers.dev.memo.application.MemoService;
import kr.teammangers.dev.memo.dto.MemoDto;
import kr.teammangers.dev.memo.dto.req.CreateMemoReq;
import kr.teammangers.dev.memo.dto.req.UpdateMemoReq;
import kr.teammangers.dev.memo.dto.res.CreateMemoRes;
import kr.teammangers.dev.memo.dto.res.GetMemoRes;
import kr.teammangers.dev.memo.dto.res.UpdateMemoRes;
import kr.teammangers.dev.tag.application.MemoTagService;
import kr.teammangers.dev.tag.application.TagService;
import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.team.application.base.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static kr.teammangers.dev.memo.mapper.MemoResMapper.MEMO_RES_MAPPER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemoCrudServiceImpl implements MemoCrudService {

    private final MemoService memoService;
    private final TagService tagService;
    private final MemoTagService memoTagService;
    private final TeamService teamService;

    @Override
    @Transactional
    public CreateMemoRes createMemo(Long teamId, CreateMemoReq req) {
        MemoDto memoDto = memoService.save(teamId, req);
        Optional.ofNullable(req.memoTagList())
                .ifPresent(memoTagList -> memoTagList
                        .forEach(tagName -> saveMemoTagFromTagName(memoDto.id(), tagName)));

        return MEMO_RES_MAPPER.toCreate(memoDto);
    }

    @Override
    public List<GetMemoRes> getMemoList(Long teamId) {
        List<MemoDto> memoDtoList = memoService.findAllDtoById(teamId);
        return memoDtoList.stream()
                .map(memoDto -> {
                    List<TagDto> tagDtoList = memoTagService.findAllTagDtoByMemoId(memoDto.id());
                    return MEMO_RES_MAPPER.toGet(memoDto, tagDtoList);
                }).toList();
    }

    @Override
    @Transactional
    public UpdateMemoRes updateMemo(Long memberId, Long teamId, UpdateMemoReq req) {
        teamService.validateTeamAdmin(teamId, memberId);
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

    private void saveMemoTagFromTagName(Long memoId, String tagName) {
        TagDto tagDto = tagService.findDtoOrSave(tagName);
        memoTagService.save(memoId, tagDto.id());
    }

}
