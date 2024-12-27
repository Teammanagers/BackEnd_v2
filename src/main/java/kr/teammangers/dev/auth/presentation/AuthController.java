package kr.teammangers.dev.auth.presentation;

import kr.teammangers.dev.auth.application.facade.AuthApiFacade;
import kr.teammangers.dev.auth.dto.TermsDto;
import kr.teammangers.dev.auth.infrastructure.security.AuthInfo;
import kr.teammangers.dev.global.common.response.ApiRes;
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
    public ApiRes<TermsDto> registerTerms(
            @AuthenticationPrincipal AuthInfo authInfo
    ) {
        TermsDto result = authApiFacade.registerTerms(authInfo.memberDto().id());
        return ApiRes.onSuccess(result);
    }

}
