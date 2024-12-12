package kr.teammangers.dev.team.dto.req;

import java.util.List;

public record UpdateTeamReq(
        Long teamId,
        String title,
        List<String> tagList
) {
}
