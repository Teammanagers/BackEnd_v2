package kr.teammangers.dev.team.mapper;

import kr.teammangers.dev.member.dto.MemberDto;
import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.team.domain.entity.TeamMember;
import kr.teammangers.dev.team.dto.TeamDto;
import kr.teammangers.dev.team.dto.TeamMemberDto;
import kr.teammangers.dev.team.dto.response.*;
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
    GetTeamRes toGet(TeamDto teamDto, String imgUrl, List<TagDto> teamTagList);

    @Mapping(target = "leader", source = "leaderDto")
    @Mapping(target = "members", source = "memberDtoList")
    GetMemberRes toGetMember(TeamMemberDto leaderDto, List<TeamMemberDto> memberDtoList);


}
