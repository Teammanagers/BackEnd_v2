package kr.teammangers.dev.auth.dto.res;

import lombok.Builder;

@Builder
public record CreateTermsRes(
        Long createdTermsId
) {
}
