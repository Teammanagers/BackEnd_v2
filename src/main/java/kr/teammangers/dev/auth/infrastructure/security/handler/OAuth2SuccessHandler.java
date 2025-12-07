package kr.teammangers.dev.auth.infrastructure.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.teammangers.dev.auth.application.service.OneTimeCodeService;
import kr.teammangers.dev.auth.application.service.TokenService;
import kr.teammangers.dev.auth.infrastructure.security.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenService tokenService;
    private final OneTimeCodeService oneTimeCodeService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AuthInfo authInfo = (AuthInfo) authentication.getPrincipal();
        String accessToken = tokenService.generateNewAccessToken(authInfo.memberDto());
        String oneTimeCode = oneTimeCodeService.generateAndStore(accessToken, authInfo.isNewMember());

        String targetUrl = UriComponentsBuilder.fromUriString("https://www.teammanagers.kr/redirect")
                .queryParam("code", oneTimeCode)
                .build()
                .toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
