package kr.teammangers.dev.document.mapper;

import kr.teammangers.dev.document.dto.DocumentDto;
import kr.teammangers.dev.document.dto.response.GetTeamDocumentRes;
import kr.teammangers.dev.tag.dto.TagDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentResMapper {

    DocumentResMapper DOCUMENT_RES_MAPPER = Mappers.getMapper(DocumentResMapper.class);

    GetTeamDocumentRes toGetTeamDocumentRes(List<DocumentDto> documentDtoList,
                                            List<List<TagDto>> tagList);
}
