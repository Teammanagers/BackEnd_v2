package kr.teammangers.dev.inquiry.application;

import kr.teammangers.dev.inquiry.dto.InquiryDto;

import java.util.List;

public interface InquiryService {
    InquiryDto save(InquiryDto inquiryDto);

    List<InquiryDto> findAllDtoByMemberId(Long memberId);
}
