package kr.teammangers.dev.memo.application.impl;

import kr.teammangers.dev.memo.application.MemoService;
import kr.teammangers.dev.memo.domain.Memo;
import kr.teammangers.dev.memo.dto.MemoDto;
import kr.teammangers.dev.memo.dto.req.CreateMemoReq;
import kr.teammangers.dev.memo.repository.MemoRepository;
import kr.teammangers.dev.team.domain.mapping.TeamManage;
import kr.teammangers.dev.team.repository.mapping.TeamManageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kr.teammangers.dev.memo.mapper.MemoMapper.MEMO_MAPPER;

@Service
@RequiredArgsConstructor
public class MemoServiceImpl implements MemoService {

    private final MemoRepository memoRepository;
    private final TeamManageRepository teamManageRepository;

    @Override
    public MemoDto save(Long teamManageId, CreateMemoReq req) {
        TeamManage teamManage = teamManageRepository.getReferenceById(teamManageId);
        return MEMO_MAPPER.toDto(insert(req, teamManage));
    }

    private Memo insert(CreateMemoReq req, TeamManage teamManage) {
        return memoRepository.save(MEMO_MAPPER.toEntity(req, teamManage));
    }

}
