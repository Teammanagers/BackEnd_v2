package kr.teammangers.dev.auth.presentation;

import kr.teammangers.dev.auth.application.facade.AuthApiFacade;
import kr.teammangers.dev.auth.infrastructure.security.AuthInfo;
import kr.teammangers.dev.auth.dto.response.CreateTermsRes;
import kr.teammangers.dev.global.common.payload.ApiRes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/auth")
public class AuthController {

    private final AuthApiFacade authApiFacade;

    @PostMapping("/terms")
    public ApiRes<CreateTermsRes> registerTerms(
            @AuthenticationPrincipal AuthInfo authInfo
    ) {
        CreateTermsRes result = authApiFacade.registerTerms(authInfo.memberDto().id());
        return ApiRes.onSuccess(result);
    }
}
