package kr.teammangers.dev.team.dto.response;

import lombok.Builder;

@Builder
public record GetTeamCodeRes(
        String teamCode
) {
}
