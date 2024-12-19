package kr.teammangers.dev.auth.dto.response;

import lombok.Builder;

@Builder
public record CreateTermsRes(
        Long createdTermsId
) {
}
