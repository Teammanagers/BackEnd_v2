package kr.teammangers.dev.inquiry.presentation;

import kr.teammangers.dev.auth.infrastructure.security.AuthInfo;
import kr.teammangers.dev.global.common.response.ApiRes;
import kr.teammangers.dev.inquiry.application.facade.InquiryApiFacade;
import kr.teammangers.dev.inquiry.dto.request.CreateInquiryReq;
import kr.teammangers.dev.inquiry.dto.request.DeleteInquiryReq;
import kr.teammangers.dev.inquiry.dto.response.CreateInquiryRes;
import kr.teammangers.dev.inquiry.dto.response.DeleteInquiryRes;
import kr.teammangers.dev.inquiry.dto.response.GetInquiryRes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/inquiry")
public class InquiryController {

    private final InquiryApiFacade inquiryApiFacade;

    @PostMapping
    public ApiRes<CreateInquiryRes> createInquiry(
            @AuthenticationPrincipal final AuthInfo auth,
            @RequestBody final CreateInquiryReq req
    ) {
        CreateInquiryRes result = inquiryApiFacade.createInquiry(auth.memberDto().id(), req);
        return ApiRes.onSuccess(result);
    }

    @GetMapping("/list")
    public ApiRes<List<GetInquiryRes>> getInquiryList(
            @AuthenticationPrincipal final AuthInfo auth
    ) {
        List<GetInquiryRes> result = inquiryApiFacade.getInquiryList(auth.memberDto().id());
        return ApiRes.onSuccess(result);
    }

    @DeleteMapping
    public ApiRes<DeleteInquiryRes> deleteInquiry(
            @RequestBody final DeleteInquiryReq req
    ) {
        DeleteInquiryRes result = inquiryApiFacade.deleteInquiry(req);
        return ApiRes.onSuccess(result);
    }

}
