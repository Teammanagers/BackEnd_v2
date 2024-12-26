package kr.teammangers.dev.team.application.service;

import kr.teammangers.dev.global.error.code.ErrorStatus;
import kr.teammangers.dev.global.error.exception.GeneralException;
import kr.teammangers.dev.memo.domain.entity.Folder;
import kr.teammangers.dev.memo.domain.repository.FolderRepository;
import kr.teammangers.dev.schedule.domain.entity.TimeSlot;
import kr.teammangers.dev.schedule.domain.repository.TimeSlotRepository;
import kr.teammangers.dev.team.domain.entity.Team;
import kr.teammangers.dev.team.domain.repository.TeamRepository;
import kr.teammangers.dev.team.dto.TeamDto;
import kr.teammangers.dev.team.dto.request.CreateTeamReq;
import kr.teammangers.dev.team.dto.request.UpdateTeamPasswordReq;
import kr.teammangers.dev.team.dto.request.UpdateTeamReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

import static kr.teammangers.dev.global.error.code.ErrorStatus.TEAM_NO_AUTHORITY;
import static kr.teammangers.dev.team.mapper.TeamMapper.TEAM_MAPPER;
import static kr.teammangers.dev.team.mapper.TeamReqMapper.TEAM_REQ_MAPPER;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final FolderRepository folderRepository;

    public TeamDto save(CreateTeamReq req, Long folderId) {
        Team team = insert(req, folderId);
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
        if (req.title() != null && !req.title().isEmpty()) {
            team.update(req);
        }
        return TEAM_MAPPER.toDto(team);
    }

    public TeamDto updatePassword(UpdateTeamPasswordReq req) {
        Team team = findById(req.teamId());
        if(req.password() != null && !req.password().isEmpty()) {
            team.updatePassword(req);
        }
        return TEAM_MAPPER.toDto(team);
    }

    public TeamDto complete(Long teamId) {
        Team team = findById(teamId);
        team.complete();
        return TEAM_MAPPER.toDto(team);
    }

    public void validateTeamAdmin(Long teamId, Long memberId) {
        if (!Objects.equals(findById(teamId).getCreatedBy(), memberId)) {
            throw new GeneralException(TEAM_NO_AUTHORITY);
        }
    }

    private Team insert(CreateTeamReq req, Long folderId) {
        TimeSlot timeSlot = timeSlotRepository.save(TimeSlot.builder().build());
        String generatedCode = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8).toUpperCase();
        Folder folder = folderRepository.getReferenceById(folderId);
        return teamRepository.save(TEAM_REQ_MAPPER.toEntity(req, generatedCode, folder, timeSlot));
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
