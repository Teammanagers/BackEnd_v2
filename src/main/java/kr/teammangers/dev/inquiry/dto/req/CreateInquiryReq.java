package kr.teammangers.dev.inquiry.dto.req;

import kr.teammangers.dev.inquiry.dto.enums.InquiryType;

public record CreateInquiryReq(
        InquiryType inquiryType,
        String content
) {
}
