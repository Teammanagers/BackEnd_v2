package kr.teammangers.dev.auth.presentation;

import kr.teammangers.dev.auth.application.TermsCrudService;
import kr.teammangers.dev.auth.dto.AuthInfo;
import kr.teammangers.dev.auth.dto.res.CreateTermsRes;
import kr.teammangers.dev.common.payload.ApiRes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/auth")
public class AuthController {

    private final TermsCrudService termsCrudService;

    @PostMapping("/terms")
    public ApiRes<CreateTermsRes> registerTerms(
            @AuthenticationPrincipal AuthInfo authInfo
    ) {
        CreateTermsRes result = termsCrudService.registerTerms(authInfo.memberDto().id());
        return ApiRes.onSuccess(result);
    }
}
