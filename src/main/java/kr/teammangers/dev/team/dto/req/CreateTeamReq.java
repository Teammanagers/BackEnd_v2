package kr.teammangers.dev.team.dto.req;

import java.util.List;

public record CreateTeamReq(
        String title,
        String password,
        String code,
        List<String> teamTagList
) {
}
