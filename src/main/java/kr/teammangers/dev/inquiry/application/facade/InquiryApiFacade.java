package kr.teammangers.dev.inquiry.application.facade;

import kr.teammangers.dev.inquiry.application.service.InquiryService;
import kr.teammangers.dev.inquiry.dto.InquiryDto;
import kr.teammangers.dev.inquiry.dto.request.CreateInquiryReq;
import kr.teammangers.dev.inquiry.dto.request.DeleteInquiryReq;
import kr.teammangers.dev.inquiry.dto.response.CreateInquiryRes;
import kr.teammangers.dev.inquiry.dto.response.DeleteInquiryRes;
import kr.teammangers.dev.inquiry.dto.response.GetInquiryRes;
import kr.teammangers.dev.inquiry.mapper.InquiryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.teammangers.dev.inquiry.mapper.InquiryResMapper.INQUIRY_RES_MAPPER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InquiryApiFacade {

    private final InquiryService inquiryService;

    @Transactional
    public CreateInquiryRes createInquiry(Long memberId, CreateInquiryReq req) {
        InquiryDto inquiryDto = InquiryMapper.INQUIRY_MAPPER.toDto(memberId, req);
        return INQUIRY_RES_MAPPER.toCreate(inquiryService.save(inquiryDto));
    }

    public List<GetInquiryRes> getInquiryList(Long memberId) {
        List<InquiryDto> dtoList = inquiryService.findAllDtoByMemberId(memberId);
        return dtoList.stream()
                .map(INQUIRY_RES_MAPPER::toGet)
                .toList();
    }

    @Transactional
    public DeleteInquiryRes deleteInquiry(DeleteInquiryReq req) {
        inquiryService.deleteByInquiryId(req.inquiryId());
        return INQUIRY_RES_MAPPER.toDelete(req.inquiryId());
    }

}
