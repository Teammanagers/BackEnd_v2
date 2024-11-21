package kr.teammangers.dev.memo.application.impl;

import kr.teammangers.dev.memo.application.MemoCrudService;
import kr.teammangers.dev.memo.application.MemoService;
import kr.teammangers.dev.memo.dto.req.CreateMemoReq;
import kr.teammangers.dev.team.application.base.TeamManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemoCrudServiceImpl implements MemoCrudService {

    private final MemoService memoService;
    private final TeamManageService teamManageService;

    @Override
    @Transactional
    public void createMemo(Long memberId, Long teamId, CreateMemoReq req) {
        Long teamManageId = teamManageService.findIdByTeamIdAndMemberId(teamId, memberId);
        memoService.save(teamManageId, req);
    }

}
