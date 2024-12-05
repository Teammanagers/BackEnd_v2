package kr.teammangers.dev.inquiry.presentation;

import kr.teammangers.dev.auth.dto.AuthInfo;
import kr.teammangers.dev.common.payload.ApiRes;
import kr.teammangers.dev.inquiry.application.InquiryCrudService;
import kr.teammangers.dev.inquiry.dto.req.CreateInquiryReq;
import kr.teammangers.dev.inquiry.dto.res.CreateInquiryRes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/inquiry")
public class InquiryController {

    private final InquiryCrudService inquiryCrudService;

    @PostMapping
    public ApiRes<CreateInquiryRes> createInquiry(
            @AuthenticationPrincipal final AuthInfo auth,
            @RequestBody final CreateInquiryReq req
    ) {
        CreateInquiryRes result = inquiryCrudService.createInquiry(auth.memberDto().id(), req);
        return ApiRes.onSuccess(result);
    }

}
