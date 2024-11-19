package kr.teammangers.dev.team.mapper;

import kr.teammangers.dev.s3.domain.S3FileInfo;
import kr.teammangers.dev.team.domain.Team;
import kr.teammangers.dev.team.domain.mapping.TeamImg;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TeamImgMapper {

    TeamImgMapper TEAM_IMG_MAPPER = Mappers.getMapper(TeamImgMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "team", source = "team")
    @Mapping(target = "s3FileInfo", source = "s3FileInfo")
    TeamImg toEntity(Team team, S3FileInfo s3FileInfo);

}
