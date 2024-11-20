package kr.teammangers.dev.team.mapper;

import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.team.dto.TeamDto;
import kr.teammangers.dev.team.dto.res.GetTeamRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeamResMapper {

    TeamResMapper TEAM_RES_MAPPER = Mappers.getMapper(TeamResMapper.class);

    @Mapping(target = "team", source = "teamDto")
    @Mapping(target = "imgUrl", source = "imgUrl")
    @Mapping(target = "teamTagList", source = "teamTagList")
    GetTeamRes toGetTeamRes(TeamDto teamDto, String imgUrl, List<TagDto> teamTagList);

}
