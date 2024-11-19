package kr.teammangers.dev.team.mapper;

import kr.teammangers.dev.team.domain.Team;
import kr.teammangers.dev.team.dto.TeamDto;
import kr.teammangers.dev.team.dto.req.CreateTeamReq;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    TeamMapper TEAM_MAPPER = Mappers.getMapper(TeamMapper.class);

    @Mapping(target = "id", ignore = true)
    Team toEntity(TeamDto teamDto);

    @Mapping(target = "id", ignore = true)
    Team toEntity(CreateTeamReq req);

    TeamDto toDto(Team team);

}
