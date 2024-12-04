package kr.teammangers.dev.team.mapper;

import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.team.dto.TeamDto;
import kr.teammangers.dev.team.dto.res.CreateTeamRes;
import kr.teammangers.dev.team.dto.res.GetTeamCodeRes;
import kr.teammangers.dev.team.dto.res.GetTeamRes;
import kr.teammangers.dev.team.dto.res.JoinTeamRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeamResMapper {

    TeamResMapper TEAM_RES_MAPPER = Mappers.getMapper(TeamResMapper.class);

    @Mapping(target = "createdTeamId", source = "id")
    CreateTeamRes toCreate(TeamDto teamDto);

    @Mapping(target = "team", source = "teamDto")
    @Mapping(target = "imgUrl", source = "imgUrl")
    @Mapping(target = "teamTagList", source = "teamTagList")
    GetTeamRes toGet(TeamDto teamDto, String imgUrl, List<TagDto> teamTagList);

    GetTeamCodeRes toGetTeamCode(String teamCode);

    @Mapping(target = "createdTeamManageId", source = "teamManageId")
    JoinTeamRes toJoin(Long teamManageId);

}
