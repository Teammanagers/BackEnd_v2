package kr.teammangers.dev.team.application.impl;

import kr.teammangers.dev.team.application.TeamService;
import kr.teammangers.dev.team.domain.Team;
import kr.teammangers.dev.team.dto.TeamDto;
import kr.teammangers.dev.team.dto.req.CreateTeamReq;
import kr.teammangers.dev.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kr.teammangers.dev.team.mapper.TeamMapper.TEAM_MAPPER;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Override
    public TeamDto save(CreateTeamReq req) {
        Team team = insertTeam(req);
        return TEAM_MAPPER.toDto(team);
    }

    private Team insertTeam(CreateTeamReq req) {
        return teamRepository.save(TEAM_MAPPER.toEntity(req));
    }
}
