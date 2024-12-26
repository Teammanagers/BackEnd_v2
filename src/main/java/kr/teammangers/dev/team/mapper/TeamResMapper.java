package kr.teammangers.dev.team.mapper;

import kr.teammangers.dev.member.dto.MemberDto;
import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.team.dto.TeamDto;
import kr.teammangers.dev.team.dto.response.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeamResMapper {

    TeamResMapper TEAM_RES_MAPPER = Mappers.getMapper(TeamResMapper.class);

    @Mapping(target = "createdTeamId", source = "id")
    @Mapping(target = "teamCode", source = "code")
    CreateTeamRes toCreate(TeamDto teamDto);

    @Mapping(target = "team", source = "teamDto")
    @Mapping(target = "imgUrl", source = "imgUrl")
    @Mapping(target = "teamTagList", source = "teamTagList")
    GetTeamRes toGet(TeamDto teamDto, String imgUrl, List<TagDto> teamTagList);

    @Mapping(target = "teamMemberId", source = "teamMemberId")
    @Mapping(target = "member", source = "memberDto")
    @Mapping(target = "imgUrl", source = "imgUrl")
    @Mapping(target = "grantedRoleList", source = "grantedRoleList")
    GetMemberRes toGetMember(Long teamMemberId, MemberDto memberDto, String imgUrl, List<TagDto> grantedRoleList);

    @Mapping(target = "updatedTeamId", source = "id")
    UpdateTeamRes toUpdate(TeamDto teamDto);

    @Mapping(target = "createdTeamMemberId", source = "teamMemberId")
    JoinTeamRes toJoin(Long teamMemberId, Long teamId);

    @Mapping(target = "team", source = "teamDto")
    @Mapping(target = "completedAt", source = "teamDto.updatedAt")
    @Mapping(target = "completedBy", source = "teamDto.updatedBy")
    CompleteTeamRes toComplete(TeamDto teamDto);
}
