package kr.teammangers.dev.inquiry.dto.response;

import lombok.Builder;

@Builder
public record CreateInquiryRes(
        Long createdInquiryId
) {
}
