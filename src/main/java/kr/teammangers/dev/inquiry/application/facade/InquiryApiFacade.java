package kr.teammangers.dev.inquiry.application.facade;

import kr.teammangers.dev.inquiry.application.service.InquiryService;
import kr.teammangers.dev.inquiry.dto.InquiryDto;
import kr.teammangers.dev.inquiry.dto.request.CreateInquiryReq;
import kr.teammangers.dev.inquiry.dto.response.GetInquiryRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.teammangers.dev.inquiry.mapper.InquiryMapper.INQUIRY_MAPPER;
import static kr.teammangers.dev.inquiry.mapper.InquiryResMapper.INQUIRY_RES_MAPPER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InquiryApiFacade {

    private final InquiryService inquiryService;

    @Transactional
    public InquiryDto createInquiry(Long memberId, CreateInquiryReq req) {
        InquiryDto inquiryDto = INQUIRY_MAPPER.toDto(memberId, req);
        return inquiryService.save(inquiryDto);
    }

    public List<GetInquiryRes> getInquiryList(Long memberId) {
        List<InquiryDto> dtoList = inquiryService.findAllDtoByMemberId(memberId);
        return dtoList.stream()
                .map(INQUIRY_RES_MAPPER::toGet)
                .toList();
    }

    @Transactional
    public Long deleteInquiry(Long inquiryId) {
        inquiryService.deleteByInquiryId(inquiryId);
        return inquiryId;
    }

}
