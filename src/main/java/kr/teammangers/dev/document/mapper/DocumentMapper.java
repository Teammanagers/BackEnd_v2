package kr.teammangers.dev.document.mapper;

import kr.teammangers.dev.document.domain.entity.Document;
import kr.teammangers.dev.document.dto.DocumentDto;
import kr.teammangers.dev.document.dto.response.GetTeamDocumentRes;
import kr.teammangers.dev.s3.domain.entity.S3FileInfo;
import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.team.domain.entity.TeamMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    DocumentMapper DOCUMENT_MAPPER = Mappers.getMapper(DocumentMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teamMember", source = "teamMember")
    @Mapping(target = "s3FileInfo", source = "s3FileInfo")
    Document toEntity(TeamMember teamMember, S3FileInfo s3FileInfo);

    @Mapping(target = "teamMemberId", source = "teamMember.id")
    @Mapping(target = "s3FileInfoId", source = "s3FileInfo.id")
    DocumentDto toDto(Document document);

 }
