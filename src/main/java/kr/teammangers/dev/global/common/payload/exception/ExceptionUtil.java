package kr.teammangers.dev.global.common.payload.exception;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import kr.teammangers.dev.global.common.payload.ApiRes;
import kr.teammangers.dev.global.common.payload.code.dto.enums.ErrorStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

public class ExceptionUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void handleAuthException(HttpServletResponse response, ErrorStatus errorStatus) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(errorStatus.getHttpStatus().value());
        response.setCharacterEncoding("UTF-8");

        ApiRes<Object> body = ApiRes.onFailure(errorStatus.getCode(), errorStatus.getMessage(), null);
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
