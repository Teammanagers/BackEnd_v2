package kr.teammangers.dev.inquiry.presentation;

import kr.teammangers.dev.auth.infrastructure.security.AuthInfo;
import kr.teammangers.dev.global.common.payload.ApiRes;
import kr.teammangers.dev.inquiry.application.InquiryCrudService;
import kr.teammangers.dev.inquiry.dto.req.CreateInquiryReq;
import kr.teammangers.dev.inquiry.dto.req.DeleteInquiryReq;
import kr.teammangers.dev.inquiry.dto.res.CreateInquiryRes;
import kr.teammangers.dev.inquiry.dto.res.DeleteInquiryRes;
import kr.teammangers.dev.inquiry.dto.res.GetInquiryRes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/list")
    public ApiRes<List<GetInquiryRes>> getInquiryList(
            @AuthenticationPrincipal final AuthInfo auth
    ) {
        List<GetInquiryRes> result = inquiryCrudService.getInquiryList(auth.memberDto().id());
        return ApiRes.onSuccess(result);
    }

    @DeleteMapping
    public ApiRes<DeleteInquiryRes> deleteInquiry(
            @RequestBody final DeleteInquiryReq req
    ) {
        DeleteInquiryRes result = inquiryCrudService.deleteInquiry(req);
        return ApiRes.onSuccess(result);
    }

}
