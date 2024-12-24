package kr.teammangers.dev.team.dto.response;

import lombok.Builder;

@Builder
public record CreateTeamRes(
        Long createdTeamId,
        Long teamCode
) {
}
