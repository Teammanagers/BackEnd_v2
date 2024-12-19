package kr.teammangers.dev.team.application;

import kr.teammangers.dev.global.common.payload.code.dto.enums.ErrorStatus;
import kr.teammangers.dev.global.common.payload.exception.GeneralException;
import kr.teammangers.dev.team.dto.TeamDto;
import kr.teammangers.dev.team.dto.req.JoinTeamReq;
import kr.teammangers.dev.team.dto.res.JoinTeamRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static kr.teammangers.dev.team.mapper.TeamResMapper.TEAM_RES_MAPPER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamMembershipService {

    private final TeamService teamService;
    private final TeamManageService teamManageService;

    @Transactional
    public JoinTeamRes joinTeam(Long memberId, Long teamId, JoinTeamReq req) {
        TeamDto teamDto = teamService.findDtoById(teamId);

        if (validPassword(teamDto, req.password())) {
            throw new GeneralException(ErrorStatus.TEAM_MISMATCH_PASSWORD);
        }
        if (isAlreadyJoin(teamDto, memberId)) {
            throw new GeneralException(ErrorStatus.TEAM_ALREADY_JOIN);
        }
        Long teamManageId = teamManageService.save(teamId, memberId);
        return TEAM_RES_MAPPER.toJoin(teamManageId);
    }

    private boolean validPassword(TeamDto teamDto, String password) {
        return !Objects.equals(teamDto.password(), password);
    }

    private boolean isAlreadyJoin(TeamDto teamDto, Long memberId) {
        return teamManageService.exists(teamDto.id(), memberId);
    }

}
