package kr.teammangers.dev.team.dto.response;

import kr.teammangers.dev.team.dto.TeamDto;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CompleteTeamRes(
        TeamDto team,
        LocalDateTime completedAt,
        Long completedBy
) {
}
