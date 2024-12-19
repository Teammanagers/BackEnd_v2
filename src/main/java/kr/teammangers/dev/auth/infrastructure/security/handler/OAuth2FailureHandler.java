package kr.teammangers.dev.auth.infrastructure.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.teammangers.dev.auth.constant.AuthErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2FailureHandler implements AuthenticationFailureHandler {

    @Value("${url.redirect.failure}")
    private String failureRedirectUrl;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        log.error(AuthErrorMessage.OAUTH2_AUTHENTICATION_FAILED, exception);
        String errorMessage = determineErrorMessage(exception);
        String redirectUrl = buildRedirectUrlWithError(errorMessage);

        // 에러 상황 전달
        response.sendRedirect(redirectUrl);
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, AuthErrorMessage.SOCIAL_LOGIN_FAILED);
    }

    private String determineErrorMessage(AuthenticationException exception) {
        if (exception instanceof OAuth2AuthenticationException) {
            return AuthErrorMessage.SOCIAL_LOGIN_FAILED;
        }
        return AuthErrorMessage.OAUTH2_AUTHENTICATION_FAILED;
    }

    private String buildRedirectUrlWithError(String errorMessage) {
        return UriComponentsBuilder.fromUriString(failureRedirectUrl)
                .queryParam("error", errorMessage)
                .build()
                .toUriString();
    }

}
