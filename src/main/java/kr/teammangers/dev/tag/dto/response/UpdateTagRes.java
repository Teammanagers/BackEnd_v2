package kr.teammangers.dev.tag.dto.response;

import lombok.Builder;

@Builder
public record UpdateTagRes(
        Long updatedTargetId
) {
}
