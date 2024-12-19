package kr.teammangers.dev.inquiry.dto.response;

import kr.teammangers.dev.inquiry.dto.InquiryDto;
import lombok.Builder;

@Builder
public record GetInquiryRes(
        InquiryDto inquiryDto
) {
}
