package kr.teammangers.dev.inquiry.dto.res;

import kr.teammangers.dev.inquiry.dto.InquiryDto;
import lombok.Builder;

@Builder
public record GetInquiryRes(
        InquiryDto inquiryDto
) {
}
