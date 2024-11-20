package kr.teammangers.dev.team.application.base;

import kr.teammangers.dev.team.dto.TeamDto;

import java.util.List;

public interface TeamManageService {
    boolean exists(Long teamId, Long memberId);

    Long save(Long teamId, Long memberId);

    List<TeamDto> findAllTeamDtoByMemberId(Long memberId);
}
