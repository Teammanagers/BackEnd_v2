package kr.teammangers.dev.memo.application.impl;

import kr.teammangers.dev.memo.application.MemoService;
import kr.teammangers.dev.memo.domain.Memo;
import kr.teammangers.dev.memo.dto.MemoDto;
import kr.teammangers.dev.memo.dto.req.CreateMemoReq;
import kr.teammangers.dev.memo.repository.MemoRepository;
import kr.teammangers.dev.team.domain.Team;
import kr.teammangers.dev.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.teammangers.dev.memo.mapper.MemoMapper.MEMO_MAPPER;

@Service
@RequiredArgsConstructor
public class MemoServiceImpl implements MemoService {

    private final MemoRepository memoRepository;
    private final TeamRepository teamRepository;

    @Override
    public MemoDto save(Long teamId, CreateMemoReq req) {
        Team team = teamRepository.getReferenceById(teamId);
        return MEMO_MAPPER.toDto(insert(req, team));
    }


    @Override
    public List<MemoDto> findAllDtoById(Long teamId) {
        return memoRepository.findAllByTeam_Id(teamId).stream()
                .map(MEMO_MAPPER::toDto)
                .toList();
    }

    private Memo insert(CreateMemoReq req, Team team) {
        return memoRepository.save(MEMO_MAPPER.toEntity(req, team));
    }

}
