package kr.teammangers.dev.team.dto.request;

import java.util.List;

public record CreateTeamReq(
        String title,
        String code,
        List<String> teamTagList
) {
}
