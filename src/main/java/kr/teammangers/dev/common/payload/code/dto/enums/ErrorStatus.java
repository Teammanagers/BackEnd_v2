package kr.teammangers.dev.common.payload.code.dto.enums;

import kr.teammangers.dev.common.payload.code.base.ErrorBaseCode;
import kr.teammangers.dev.common.payload.code.dto.ErrorReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static kr.teammangers.dev.common.payload.code.dto.enums.CommonErrorMessage.IS_ALREADY_EXISTS;
import static kr.teammangers.dev.common.payload.code.dto.enums.CommonErrorMessage.NOT_FOUND_TEMPLATE;
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
    TERMS_ALREADY_EXISTS(FORBIDDEN, "TERMS4030", IS_ALREADY_EXISTS),

    // Team
    TEAM_NOT_FOUND(NOT_FOUND, "TEAM404", entityNotFoundMessage("팀")),
    TEAM_MISMATCH_PASSWORD(BAD_REQUEST, "TEAM4000", "비밀번호가 틀렸습니다."),
    TEAM_ALREADY_JOIN(FORBIDDEN, "TEAM4030", "이미 가입된 사용자입니다."),

    // TeamManage
    TEAMMANAGE_NOT_FOUND(NOT_FOUND, "TEAMMANAGE404", entityNotFoundMessage("TeamManage")),

    TODO_NOT_FOUND(NOT_FOUND, "TODO404", entityNotFoundMessage("TODO")),
    TODO_FORBIDDEN(FORBIDDEN, "TODO403", "TODO에 접근 권한이 없습니다."),

    // S3
    S3_NOT_FOUND_FROM_BUCKET(NOT_FOUND, "S34040", entityNotFoundMessage("S3 버킷에서 파일")),

    // Notice
    NOTICE_NO_AUTHORITY(UNAUTHORIZED, "NOTICE4010", "팀 공지 관리 권한이 없습니다"),
    NOTICE_NOT_FOUND(NOT_FOUND, "NOTICE4040", entityNotFoundMessage("공지")),

    // 매핑 테이블
    TEAM_IMG_NOT_FOUND(NOT_FOUND, "TEAM_IMG_404", entityNotFoundMessage("팀-S3 매핑 테이블"));

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
