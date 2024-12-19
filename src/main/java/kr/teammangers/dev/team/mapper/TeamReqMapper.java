package kr.teammangers.dev.team.mapper;

import kr.teammangers.dev.schedule.domain.entity.TimeSlot;
import kr.teammangers.dev.team.domain.entity.Team;
import kr.teammangers.dev.team.dto.request.CreateTeamReq;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TeamReqMapper {

    TeamReqMapper TEAM_REQ_MAPPER = Mappers.getMapper(TeamReqMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rootFolderId", source = "rootFolderId")
    @Mapping(target = "timeSlot", source = "timeSlot")
    Team toEntity(CreateTeamReq req, Long rootFolderId, TimeSlot timeSlot);

}
