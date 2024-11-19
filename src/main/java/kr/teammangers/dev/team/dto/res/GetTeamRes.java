package kr.teammangers.dev.team.dto.res;

import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.team.dto.TeamDto;

import java.util.List;

public record GetTeamRes(
        TeamDto team,
        String imgUrl,
        List<TagDto> teamTagList
) {
}
