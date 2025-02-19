package kr.teammangers.dev.document.dto;

import lombok.Builder;

@Builder
public record DocumentDto(
        Long id,

        Long teamMemberId,
        Long s3FileInfoId

) {
}
