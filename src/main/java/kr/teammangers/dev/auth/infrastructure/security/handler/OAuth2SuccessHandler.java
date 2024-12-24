package kr.teammangers.dev.auth.infrastructure.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.teammangers.dev.auth.application.service.TokenService;
import kr.teammangers.dev.auth.infrastructure.security.AuthInfo;
import kr.teammangers.dev.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final TokenService tokenService;

    @Value("${url.redirect}")
    private String redirectUrl;

    @Value("${url.redirect.error}")
    private String errorRedirectUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        try {
            AuthInfo authInfo = (AuthInfo) authentication.getPrincipal();

            // 토큰 발급
            setTokens(response, authInfo.memberDto());

            // 새로운 회원인 경우 다른 페이지로 리다이렉트
            String targetUrl = determineTargetUrl(authInfo);

            response.sendRedirect(targetUrl);

        } catch (Exception e) {
            // 토큰 발급 실패 예외 처리
            log.error("OAuth2 인증 성공 후 처리 실패", e);
            response.sendRedirect(errorRedirectUrl);
            response.sendRedirect(redirectUrl);
        }
    }

    private void setTokens(HttpServletResponse response, MemberDto memberDto) {
        tokenService.provideAccessToken(response, memberDto);
        tokenService.provideRefreshToken(response, memberDto);
    }

    private String determineTargetUrl(AuthInfo authInfo) {
        // 추가 정보 입력이 필요한 새로운 회원인 경우
        if (authInfo.isNewMember()) {
//            return redirectUrl + "/additional-info";
        }
        return redirectUrl;
    }

}
