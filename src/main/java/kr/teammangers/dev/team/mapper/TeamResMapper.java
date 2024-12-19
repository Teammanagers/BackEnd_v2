package kr.teammangers.dev.team.mapper;

import kr.teammangers.dev.member.dto.MemberDto;
import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.team.dto.TeamDto;
import kr.teammangers.dev.team.dto.res.*;
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

    @Mapping(target = "teamManageId", source = "teamManageId")
    @Mapping(target = "member", source = "memberDto")
    @Mapping(target = "imgUrl", source = "imgUrl")
    @Mapping(target = "grantedRoleList", source = "grantedRoleList")
    GetMemberRes toGetMember(Long teamManageId, MemberDto memberDto, String imgUrl, List<TagDto> grantedRoleList);

    GetTeamCodeRes toGetTeamCode(String teamCode);

    @Mapping(target = "updatedTeamId", source = "id")
    UpdateTeamRes toUpdate(TeamDto teamDto);

    @Mapping(target = "createdTeamManageId", source = "teamManageId")
    JoinTeamRes toJoin(Long teamManageId);

}
