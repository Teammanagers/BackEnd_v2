package kr.teammangers.dev.inquiry.application.service;

import kr.teammangers.dev.inquiry.domain.entity.Inquiry;
import kr.teammangers.dev.inquiry.domain.repository.InquiryRepository;
import kr.teammangers.dev.inquiry.dto.InquiryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.teammangers.dev.inquiry.mapper.InquiryMapper.INQUIRY_MAPPER;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    public InquiryDto save(InquiryDto inquiryDto) {
        Inquiry inquiry = INQUIRY_MAPPER.toEntity(inquiryDto);
        return INQUIRY_MAPPER.toDto(inquiryRepository.save(inquiry));
    }

    public List<InquiryDto> findAllDtoByMemberId(Long memberId) {
        List<Inquiry> inquiryList = inquiryRepository.findAllByMemberId(memberId);
        return inquiryList.stream()
                .map(INQUIRY_MAPPER::toDto)
                .toList();
    }

    public void deleteByInquiryId(Long inquiryId) {
        inquiryRepository.deleteById(inquiryId);
    }
}
