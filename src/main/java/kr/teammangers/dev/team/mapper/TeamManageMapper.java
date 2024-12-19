package kr.teammangers.dev.team.mapper;

import kr.teammangers.dev.member.domain.entity.Member;
import kr.teammangers.dev.schedule.domain.entity.TimeSlot;
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
    @Mapping(target = "timeSlot", source = "timeSlot")
    TeamManage toEntity(Team team, Member member, TimeSlot timeSlot);

}
