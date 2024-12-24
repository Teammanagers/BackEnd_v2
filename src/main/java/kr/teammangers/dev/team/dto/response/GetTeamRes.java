package kr.teammangers.dev.team.dto.response;

import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.team.dto.TeamDto;
import lombok.Builder;

import java.util.List;

@Builder
public record GetTeamRes(
        TeamDto team,
        String imgUrl,
        List<TagDto> teamTagList
) {
}
