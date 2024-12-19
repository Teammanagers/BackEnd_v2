package kr.teammangers.dev.s3.mapper;

import kr.teammangers.dev.s3.domain.entity.S3FileInfo;
import kr.teammangers.dev.s3.dto.S3FileInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface S3Mapper {

    S3Mapper S3_MAPPER = Mappers.getMapper(S3Mapper.class);

    @Mapping(target = "id", ignore = true)
    S3FileInfo toEntity(S3FileInfoDto s3FileInfoDto);

    S3FileInfoDto toDto(S3FileInfo s3FileInfo);

}
