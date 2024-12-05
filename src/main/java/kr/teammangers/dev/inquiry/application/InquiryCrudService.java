package kr.teammangers.dev.inquiry.application;

import kr.teammangers.dev.inquiry.dto.req.CreateInquiryReq;
import kr.teammangers.dev.inquiry.dto.res.CreateInquiryRes;

public interface InquiryCrudService {
    CreateInquiryRes createInquiry(Long memberId, CreateInquiryReq req);
}
