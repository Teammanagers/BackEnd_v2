package kr.teammangers.dev.team.dto.request;

import java.util.List;

public record UpdateTeamReq(
        Long teamId,
        String title,
        List<String> tagList
) {
}
