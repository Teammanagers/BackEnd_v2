package kr.teammangers.dev.s3.mapper;

import kr.teammangers.dev.s3.domain.entity.S3FileInfo;
import kr.teammangers.dev.s3.domain.entity.TeamImg;
import kr.teammangers.dev.team.domain.entity.Team;
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
