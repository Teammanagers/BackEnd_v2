package kr.teammangers.dev.inquiry.application;

import kr.teammangers.dev.inquiry.dto.req.CreateInquiryReq;
import kr.teammangers.dev.inquiry.dto.res.CreateInquiryRes;
import kr.teammangers.dev.inquiry.dto.res.GetInquiryRes;

import java.util.List;

public interface InquiryCrudService {
    CreateInquiryRes createInquiry(Long memberId, CreateInquiryReq req);

    List<GetInquiryRes> getInquiryList(Long memberId);
}
