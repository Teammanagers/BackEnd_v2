package kr.teammangers.dev.team.dto.request;

import java.util.List;

public record CreateTeamReq(
        String title,
        List<String> teamTagList
) {
}
