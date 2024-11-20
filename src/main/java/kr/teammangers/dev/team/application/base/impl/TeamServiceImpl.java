package kr.teammangers.dev.team.application.base.impl;

import kr.teammangers.dev.common.payload.code.dto.enums.ErrorStatus;
import kr.teammangers.dev.common.payload.exception.GeneralException;
import kr.teammangers.dev.team.application.base.TeamService;
import kr.teammangers.dev.team.domain.Team;
import kr.teammangers.dev.team.dto.TeamDto;
import kr.teammangers.dev.team.dto.req.CreateTeamReq;
import kr.teammangers.dev.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static kr.teammangers.dev.common.payload.code.dto.enums.ErrorStatus.NOTICE_NO_AUTHORITY;
import static kr.teammangers.dev.team.mapper.TeamMapper.TEAM_MAPPER;
import static kr.teammangers.dev.team.mapper.TeamReqMapper.TEAM_REQ_MAPPER;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Override
    public TeamDto save(CreateTeamReq req) {
        Team team = insert(req);
        return TEAM_MAPPER.toDto(team);
    }

    @Override
    public TeamDto findDtoByTeamCode(String teamCode) {
        return TEAM_MAPPER.toDto(findByTeamCode(teamCode));
    }

    @Override
    public TeamDto findDtoById(Long id) {
        return TEAM_MAPPER.toDto(findById(id));
    }

    @Override
    public void validateTeamAdmin(Long teamId, Long memberId) {
        if(!Objects.equals(findById(teamId).getCreatedBy(), memberId)) {
            throw new GeneralException(NOTICE_NO_AUTHORITY);
        }
    }

    private Team insert(CreateTeamReq req) {
        return teamRepository.save(TEAM_REQ_MAPPER.toEntity(req));
    }

    private Team findByTeamCode(String teamCode) {
        return teamRepository.findByCode(teamCode)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TEAM_NOT_FOUND));
    }

    private Team findById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TEAM_NOT_FOUND));
    }

}
