package kr.teammangers.dev.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.teammangers.dev.auth.constants.AuthErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class OAuth2FailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error(AuthErrorMessage.OAUTH2_AUTHENTICATION_FAILED, exception);
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, AuthErrorMessage.SOCIAL_LOGIN_FAILED);
    }

}
