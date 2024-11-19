package kr.teammangers.dev.s3.dto;

import lombok.Builder;

@Builder
public record FileNameParts(
        String fileNm,
        String ext
) {
}
