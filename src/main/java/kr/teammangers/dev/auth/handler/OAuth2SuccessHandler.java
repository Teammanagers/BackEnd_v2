package kr.teammangers.dev.auth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.teammangers.dev.auth.application.TokenService;
import kr.teammangers.dev.auth.dto.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final TokenService tokenService;

    @Value("${url.redirect}")
    private String redirectUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        AuthInfo authInfo = (AuthInfo) authentication.getPrincipal();
        String accessToken = tokenService.provideAccessToken(response, authInfo.memberDto());
        String refreshToken = tokenService.provideRefreshToken(response, authInfo.memberDto());

//        response.setHeader(ACCESS_PREFIX.getValue(), TOKEN_PREFIX + accessToken);
//        response.setHeader(REFRESH_PREFIX.getValue(), refreshToken);
//        response.setHeader("Access-Control-Expose-Headers", ACCESS_PREFIX.getValue() + ", " + REFRESH_PREFIX.getValue());

        response.sendRedirect(redirectUrl);
    }
}
