package kr.teammangers.dev.team.dto.res;

import lombok.Builder;

@Builder
public record UpdateTeamRes(
        Long updatedTeamId
) {
}
