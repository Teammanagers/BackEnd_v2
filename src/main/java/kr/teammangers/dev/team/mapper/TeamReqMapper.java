package kr.teammangers.dev.team.mapper;

import kr.teammangers.dev.memo.domain.entity.Folder;
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
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "code", source = "teamCode")
    @Mapping(target = "isCompleted", expression = "java(false)")
    @Mapping(target = "rootFolder", source = "folder")
    @Mapping(target = "timeSlot", source = "timeSlot")
    Team toEntity(CreateTeamReq req, String teamCode, Folder folder, TimeSlot timeSlot);

}
