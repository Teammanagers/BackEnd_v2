package kr.teammangers.dev.team.application;

import kr.teammangers.dev.common.payload.code.dto.enums.ErrorStatus;
import kr.teammangers.dev.common.payload.exception.GeneralException;
import kr.teammangers.dev.schedule.domain.TimeSlot;
import kr.teammangers.dev.schedule.repository.TimeSlotRepository;
import kr.teammangers.dev.team.domain.Team;
import kr.teammangers.dev.team.dto.TeamDto;
import kr.teammangers.dev.team.dto.req.CreateTeamReq;
import kr.teammangers.dev.team.dto.req.UpdateTeamReq;
import kr.teammangers.dev.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static kr.teammangers.dev.common.payload.code.dto.enums.ErrorStatus.TEAM_NO_AUTHORITY;
import static kr.teammangers.dev.team.mapper.TeamMapper.TEAM_MAPPER;
import static kr.teammangers.dev.team.mapper.TeamReqMapper.TEAM_REQ_MAPPER;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TimeSlotRepository timeSlotRepository;

    public TeamDto save(CreateTeamReq req, Long rootFolderId) {
        Team team = insert(req, rootFolderId);
        return TEAM_MAPPER.toDto(team);
    }

    public TeamDto findDtoByTeamCode(String teamCode) {
        return TEAM_MAPPER.toDto(findByTeamCode(teamCode));
    }

    public TeamDto findDtoById(Long id) {
        return TEAM_MAPPER.toDto(findById(id));
    }

    public TeamDto update(UpdateTeamReq req) {
        Team team = findById(req.teamId());
        team.update(req);
        return TEAM_MAPPER.toDto(team);
    }

    public void validateTeamAdmin(Long teamId, Long memberId) {
        if (!Objects.equals(findById(teamId).getCreatedBy(), memberId)) {
            throw new GeneralException(TEAM_NO_AUTHORITY);
        }
    }

    private Team insert(CreateTeamReq req, Long rootFolderId) {
        TimeSlot timeSlot = timeSlotRepository.save(TimeSlot.builder().build());
        return teamRepository.save(TEAM_REQ_MAPPER.toEntity(req, rootFolderId, timeSlot));
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
