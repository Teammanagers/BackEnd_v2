package kr.teammangers.dev.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.common.contenttype.ContentType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.teammangers.dev.common.payload.ApiRes;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

import static kr.teammangers.dev.common.payload.code.dto.enums.ErrorStatus._UNAUTHORIZED;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        String body = objectMapper.writeValueAsString(ApiRes.onFailure(_UNAUTHORIZED.getCode(), _UNAUTHORIZED.getMessage(), null));

        response.setContentType(ContentType.APPLICATION_JSON.getType());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(body);
    }
}
