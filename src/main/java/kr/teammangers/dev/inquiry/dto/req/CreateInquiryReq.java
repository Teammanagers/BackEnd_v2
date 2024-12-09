package kr.teammangers.dev.inquiry.dto.req;

import kr.teammangers.dev.inquiry.enums.InquiryType;

public record CreateInquiryReq(
        InquiryType inquiryType,
        String content
) {
}
