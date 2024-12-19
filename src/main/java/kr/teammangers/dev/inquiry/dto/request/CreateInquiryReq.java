package kr.teammangers.dev.inquiry.dto.request;

import kr.teammangers.dev.inquiry.domain.enums.InquiryType;

public record CreateInquiryReq(
        InquiryType inquiryType,
        String content
) {
}
