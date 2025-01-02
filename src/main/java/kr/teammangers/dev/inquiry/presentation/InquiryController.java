package kr.teammangers.dev.inquiry.presentation;

import kr.teammangers.dev.auth.infrastructure.security.AuthInfo;
import kr.teammangers.dev.global.common.response.ApiRes;
import kr.teammangers.dev.inquiry.application.facade.InquiryApiFacade;
import kr.teammangers.dev.inquiry.dto.InquiryDto;
import kr.teammangers.dev.inquiry.dto.request.CreateInquiryReq;
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
    public ApiRes<InquiryDto> createInquiry(
            @AuthenticationPrincipal final AuthInfo auth,
            @RequestBody final CreateInquiryReq req
    ) {
        InquiryDto result = inquiryApiFacade.createInquiry(auth.memberDto().id(), req);
        return ApiRes.onSuccess(result);
    }

    @GetMapping("/list")
    public ApiRes<List<GetInquiryRes>> getInquiryList(
            @AuthenticationPrincipal final AuthInfo auth
    ) {
        List<GetInquiryRes> result = inquiryApiFacade.getInquiryList(auth.memberDto().id());
        return ApiRes.onSuccess(result);
    }

    @DeleteMapping("/{inquiryId}")
    public ApiRes<Long> deleteInquiry(
            @PathVariable("inquiryId") final Long inquiryId
    ) {
        Long result = inquiryApiFacade.deleteInquiry(inquiryId);
        return ApiRes.onSuccess(result);
    }

}
