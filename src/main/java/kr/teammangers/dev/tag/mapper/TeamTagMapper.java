package kr.teammangers.dev.tag.mapper;

import kr.teammangers.dev.tag.domain.entity.Tag;
import kr.teammangers.dev.tag.domain.entity.TeamTag;
import kr.teammangers.dev.tag.dto.TeamTagDto;
import kr.teammangers.dev.team.domain.entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TeamTagMapper {

    TeamTagMapper TEAM_TAG_MAPPER = Mappers.getMapper(TeamTagMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "team", source = "team")
    @Mapping(target = "tag", source = "tag")
    TeamTag toEntity(Team team, Tag tag);

    @Mapping(target = "tagId", source = "teamTag.tag.id")
    @Mapping(target = "teamId", source = "teamTag.team.id")
    TeamTagDto toDto(TeamTag teamTag);
}
