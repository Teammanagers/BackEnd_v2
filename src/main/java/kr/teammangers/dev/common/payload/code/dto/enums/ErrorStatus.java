package kr.teammangers.dev.common.payload.code.dto.enums;

import kr.teammangers.dev.common.payload.code.base.ErrorBaseCode;
import kr.teammangers.dev.common.payload.code.dto.ErrorReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static kr.teammangers.dev.common.payload.code.dto.enums.CommonErrorMessage.*;
import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements ErrorBaseCode {

    // 일반적인 응답
    _INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, "500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(BAD_REQUEST, "400", "잘못된 요청입니다."),
    _UNAUTHORIZED(UNAUTHORIZED, "401", "인증이 필요합니다."),
    _FORBIDDEN(FORBIDDEN, "403", "금지된 요청입니다."),
    _NOT_FOUND(NOT_FOUND, "404", "찾을 수 없습니다."),

    // Auth
    AUTH_FORBIDDEN(FORBIDDEN, "AUTH403", "OAuth2 로그인에 실패하였습니다."),
    AUTH_ILLEGAL_REGISTRATION_ID(NOT_ACCEPTABLE, "AUTH406", "Registration ID가 올바르지 않습니다."),
    AUTH_INVALID_EXPIRED_TOKEN(UNAUTHORIZED, "AUTH4010", "토큰이 유효하지 않거나, 만료된 토큰입니다."),
    AUTH_INVALID_JWT_SIGNATURE(UNAUTHORIZED, "AUTH4012", "잘못된 JWT 시그니처입니다."),

    // Member
    MEMBER_NOT_FOUND(NOT_FOUND, "MEMBER404", entityNotFoundMessage("사용자")),

    // Term
    TERMS_ALREADY_EXISTS(FORBIDDEN, "TERMS4031", IS_ALREADY_EXISTS),

    // Team
    TEAM_NOT_FOUND(NOT_FOUND, "TEAM404", entityNotFoundMessage("팀"));


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

    private static String entityNotFoundMessage(String entityName) {
        return String.format(NOT_FOUND_TEMPLATE, entityName);
    }

}
