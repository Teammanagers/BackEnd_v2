package kr.teammangers.dev.team.mapper;

import kr.teammangers.dev.member.domain.entity.Member;
import kr.teammangers.dev.schedule.domain.entity.TimeSlot;
import kr.teammangers.dev.team.domain.entity.Team;
import kr.teammangers.dev.team.domain.entity.TeamMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TeamMemberMapper {

    TeamMemberMapper TEAM_MEMBER_MAPPER = Mappers.getMapper(TeamMemberMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "team", source = "team")
    @Mapping(target = "member", source = "member")
    @Mapping(target = "timeSlot", source = "timeSlot")
    TeamMember toEntity(Team team, Member member, TimeSlot timeSlot);

}
