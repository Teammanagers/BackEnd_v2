package kr.teammangers.dev.inquiry.application.impl;

import kr.teammangers.dev.inquiry.application.InquiryService;
import kr.teammangers.dev.inquiry.domain.Inquiry;
import kr.teammangers.dev.inquiry.dto.InquiryDto;
import kr.teammangers.dev.inquiry.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.teammangers.dev.inquiry.mapper.InquiryMapper.INQUIRY_MAPPER;

@Service
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {

    private final InquiryRepository inquiryRepository;

    @Override
    public InquiryDto save(InquiryDto inquiryDto) {
        Inquiry inquiry = INQUIRY_MAPPER.toEntity(inquiryDto);
        return INQUIRY_MAPPER.toDto(inquiryRepository.save(inquiry));
    }

    @Override
    public List<InquiryDto> findAllDtoByMemberId(Long memberId) {
        List<Inquiry> inquiryList = inquiryRepository.findAllByMemberId(memberId);
        return inquiryList.stream()
                .map(INQUIRY_MAPPER::toDto)
                .toList();
    }

    @Override
    public void deleteByInquiryId(Long inquiryId) {
        inquiryRepository.deleteById(inquiryId);
    }
}
