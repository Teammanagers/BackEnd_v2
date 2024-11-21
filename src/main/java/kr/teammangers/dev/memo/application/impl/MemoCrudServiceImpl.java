package kr.teammangers.dev.memo.application.impl;

import kr.teammangers.dev.memo.application.MemoCrudService;
import kr.teammangers.dev.memo.application.MemoService;
import kr.teammangers.dev.memo.dto.MemoDto;
import kr.teammangers.dev.memo.dto.req.CreateMemoReq;
import kr.teammangers.dev.memo.dto.res.CreateMemoRes;
import kr.teammangers.dev.tag.application.MemoTagService;
import kr.teammangers.dev.tag.application.TagService;
import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.team.application.base.TeamManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kr.teammangers.dev.memo.mapper.MemoResMapper.MEMO_RES_MAPPER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemoCrudServiceImpl implements MemoCrudService {

    private final MemoService memoService;
    private final TeamManageService teamManageService;
    private final TagService tagService;
    private final MemoTagService memoTagService;

    @Override
    @Transactional
    public CreateMemoRes createMemo(Long memberId, Long teamId, CreateMemoReq req) {
        Long teamManageId = teamManageService.findIdByTeamIdAndMemberId(teamId, memberId);
        MemoDto memoDto = memoService.save(teamManageId, req);

        req.memoTagList().forEach(tagName -> {
            TagDto tagDto = tagService.findDtoOrSave(tagName);
            memoTagService.save(memoDto.id(), tagDto.id());
        });

        return MEMO_RES_MAPPER.toCreate(memoDto);
    }

}
