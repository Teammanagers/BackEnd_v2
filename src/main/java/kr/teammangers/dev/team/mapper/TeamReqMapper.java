package kr.teammangers.dev.team.mapper;

import kr.teammangers.dev.team.domain.Team;
import kr.teammangers.dev.team.dto.req.CreateTeamReq;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TeamReqMapper {

    TeamReqMapper TEAM_REQ_MAPPER = Mappers.getMapper(TeamReqMapper.class);

    @Mapping(target = "id", ignore = true)
    Team toEntity(CreateTeamReq req);

}
