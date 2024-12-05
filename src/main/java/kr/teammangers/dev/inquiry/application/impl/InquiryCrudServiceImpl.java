package kr.teammangers.dev.inquiry.application.impl;

import kr.teammangers.dev.inquiry.application.InquiryCrudService;
import kr.teammangers.dev.inquiry.application.InquiryService;
import kr.teammangers.dev.inquiry.dto.InquiryDto;
import kr.teammangers.dev.inquiry.dto.req.CreateInquiryReq;
import kr.teammangers.dev.inquiry.dto.req.DeleteInquiryReq;
import kr.teammangers.dev.inquiry.dto.res.CreateInquiryRes;
import kr.teammangers.dev.inquiry.dto.res.DeleteInquiryRes;
import kr.teammangers.dev.inquiry.dto.res.GetInquiryRes;
import kr.teammangers.dev.inquiry.mapper.InquiryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.teammangers.dev.inquiry.mapper.InquiryResMapper.INQUIRY_RES_MAPPER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InquiryCrudServiceImpl implements InquiryCrudService {

    private final InquiryService inquiryService;

    @Override
    @Transactional
    public CreateInquiryRes createInquiry(Long memberId, CreateInquiryReq req) {
        InquiryDto inquiryDto = InquiryMapper.INQUIRY_MAPPER.toDto(memberId, req);
        return INQUIRY_RES_MAPPER.toCreate(inquiryService.save(inquiryDto));
    }

    @Override
    public List<GetInquiryRes> getInquiryList(Long memberId) {
        List<InquiryDto> dtoList = inquiryService.findAllDtoByMemberId(memberId);
        return dtoList.stream()
                .map(INQUIRY_RES_MAPPER::toGet)
                .toList();
    }

    @Override
    @Transactional
    public DeleteInquiryRes deleteInquiry(DeleteInquiryReq req) {
        inquiryService.deleteByInquiryId(req.inquiryId());
        return INQUIRY_RES_MAPPER.toDelete(req.inquiryId());
    }

}
