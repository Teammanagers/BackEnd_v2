package kr.teammangers.dev.common.payload.code.dto.enums;

import kr.teammangers.dev.common.payload.code.base.ErrorBaseCode;
import kr.teammangers.dev.common.payload.code.dto.ErrorReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements ErrorBaseCode {

    // 일반적인 응답
    _INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, "500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(BAD_REQUEST, "400", "잘못된 요청입니다."),
    _UNAUTHORIZED(UNAUTHORIZED, "401", "인증이 필요합니다."),
    _FORBIDDEN(FORBIDDEN, "403", "금지된 요청입니다."),
    _NOT_FOUND(NOT_FOUND, "404", "찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDto getReason() {
        return null;
    }

    @Override
    public ErrorReasonDto getReasonHttpStatus() {
        return ErrorReasonDto.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
