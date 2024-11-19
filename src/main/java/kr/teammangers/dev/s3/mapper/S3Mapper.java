package kr.teammangers.dev.s3.mapper;

import kr.teammangers.dev.s3.domain.S3FileInfo;
import kr.teammangers.dev.s3.dto.S3FileInfoDto;
import kr.teammangers.dev.team.domain.Team;
import kr.teammangers.dev.team.domain.mapping.TeamImg;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface S3Mapper {

    S3Mapper S3_MAPPER = Mappers.getMapper(S3Mapper.class);

    @Mapping(target = "id", ignore = true)
    S3FileInfo toEntity(S3FileInfoDto s3FileInfoDto);

    S3FileInfoDto toDto(S3FileInfo s3FileInfo);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "team", source = "team")
    @Mapping(target = "s3FileInfo", source = "s3FileInfo")
    TeamImg toTeamImgEntity(Team team, S3FileInfo s3FileInfo);

}
