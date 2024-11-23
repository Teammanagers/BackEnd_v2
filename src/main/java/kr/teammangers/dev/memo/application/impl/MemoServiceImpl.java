package kr.teammangers.dev.memo.application.impl;

import kr.teammangers.dev.common.payload.exception.GeneralException;
import kr.teammangers.dev.memo.application.MemoService;
import kr.teammangers.dev.memo.domain.Memo;
import kr.teammangers.dev.memo.dto.MemoDto;
import kr.teammangers.dev.memo.dto.req.CreateMemoReq;
import kr.teammangers.dev.memo.dto.req.UpdateMemoReq;
import kr.teammangers.dev.memo.repository.MemoRepository;
import kr.teammangers.dev.team.domain.Team;
import kr.teammangers.dev.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static kr.teammangers.dev.common.payload.code.dto.enums.ErrorStatus.*;
import static kr.teammangers.dev.memo.mapper.MemoMapper.MEMO_MAPPER;

@Service
@RequiredArgsConstructor
public class MemoServiceImpl implements MemoService {

    private final MemoRepository memoRepository;
    private final TeamRepository teamRepository;

    @Override
    public MemoDto save(CreateMemoReq req) {
        Team team = teamRepository.getReferenceById(req.teamId());
        return MEMO_MAPPER.toDto(insert(req, team));
    }


    @Override
    public List<MemoDto> findAllDtoById(Long teamId) {
        return memoRepository.findAllByTeam_Id(teamId).stream()
                .map(MEMO_MAPPER::toDto)
                .toList();
    }

    @Override
    public MemoDto update(UpdateMemoReq req) {
        Memo memo = findById(req.memoId());
        memo.update(req);
        return MEMO_MAPPER.toDto(memo);
    }

    @Override
    public void deleteById(Long memoId) {
        memoRepository.deleteById(memoId);
    }

    @Override
    public void validateMemoAdmin(Long memoId, Long memberId) {
        if(!Objects.equals(findById(memoId).getCreatedBy(), memberId)) {
            throw new GeneralException(MEMO_NO_AUTHORITY);
        }
    }

    private Memo insert(CreateMemoReq req, Team team) {
        return memoRepository.save(MEMO_MAPPER.toEntity(req, team));
    }

    private Memo findById(Long id) {
        return memoRepository.findById(id)
                .orElseThrow(() -> new GeneralException(MEMO_NOT_FOUND));
    }

}
