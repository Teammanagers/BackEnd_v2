package kr.teammangers.dev.document.dto.response;

import kr.teammangers.dev.document.dto.DocumentDto;
import kr.teammangers.dev.tag.dto.TagDto;

import java.util.List;

public record GetTeamDocumentRes(
        List<DocumentDto> documentDtoList,
        List<List<TagDto>> tagDtoList
) {
}
