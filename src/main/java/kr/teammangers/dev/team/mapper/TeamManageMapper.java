package kr.teammangers.dev.team.mapper;

import kr.teammangers.dev.member.domain.Member;
import kr.teammangers.dev.team.domain.Team;
import kr.teammangers.dev.team.domain.mapping.TeamManage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TeamManageMapper {

    TeamManageMapper TEAM_MANAGE_MAPPER = Mappers.getMapper(TeamManageMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "team", source = "team")
    @Mapping(target = "member", source = "member")
    TeamManage toEntity(Team team, Member member);

}
