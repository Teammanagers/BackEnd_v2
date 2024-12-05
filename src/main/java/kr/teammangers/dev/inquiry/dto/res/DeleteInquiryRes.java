package kr.teammangers.dev.inquiry.dto.res;

import lombok.Builder;

@Builder
public record DeleteInquiryRes(
        Long deletedInquiryId
) {
}
