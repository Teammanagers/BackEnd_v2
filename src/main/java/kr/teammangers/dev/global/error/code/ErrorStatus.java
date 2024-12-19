package kr.teammangers.dev.global.error.code;

import kr.teammangers.dev.global.error.dto.ErrorReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static kr.teammangers.dev.global.error.message.CommonErrorMessage.*;
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
    TEAM_NO_AUTHORITY(UNAUTHORIZED, "TEAM4010", "권한이 없습니다."),

    // S3
    S3_NOT_FOUND_FROM_BUCKET(NOT_FOUND, "S34040", entityNotFoundMessage("S3 버킷에서 파일")),

    // Notice
    NOTICE_NO_AUTHORITY(UNAUTHORIZED, "NOTICE4010", entityNoAuthorityMessage("팀 공지")),
    NOTICE_NOT_FOUND(NOT_FOUND, "NOTICE4040", entityNotFoundMessage("공지")),

    // Memo
    MEMO_NO_AUTHORITY(UNAUTHORIZED, "MEMO4010", entityNoAuthorityMessage("해당 메모")),
    MEMO_NOT_FOUND(NOT_FOUND, "MEMO4040", entityNotFoundMessage("메모")),

    // Folder
    FOLDER_PARAMETER(BAD_REQUEST, "FOLDER4000", "올바르지 않은 요청입니다. (folderId, teamId 중 적어도 하나의 파라미터가 필요합니다.)"),
    FOLDER_NOT_FOUND(NOT_FOUND, "FOLDER4040", entityNotFoundMessage("폴더")),

    // Plan
    PLAN_NOT_FOUND(NOT_FOUND, "PLAN4040", entityNotFoundMessage("일정")),

    // TimeSlot
    TIME_SLOT_BAD_REQUEST(BAD_REQUEST, "TIME_SLOT4000", "올바르지 않은 요청입니다."),
    TIME_SLOT_HOUR(BAD_REQUEST, "TIME_SLOT4001", "시간은 0 이상 23 이하 입니다."),
    TIME_SLOT_MINUTE(BAD_REQUEST, "TIME_SLOT4002", "분은 0 이상 59 이하 입니다."),
    TIME_SLOT_INTERVAL(BAD_REQUEST, "TIME_SLOT4003", "시간 간격이 옳지 않습니다"),
    TIME_SLOT_ORDER(BAD_REQUEST, "TIME_SLOT4004", "슬롯의 전후 순서가 맞지 않습니다."),
    TIME_SLOT_NOT_FOUND(NOT_FOUND, "TIME_SLOT_4040", entityNotFoundMessage("스케줄")),

    // 매핑 테이블
    TEAM_IMG_NOT_FOUND(NOT_FOUND, "TEAM_IMG404", entityNotFoundMessage("팀-S3 매핑 테이블")),
    TEAM_MEMBER_NOT_FOUND(NOT_FOUND, "TEAM_MEMBER404", entityNotFoundMessage("팀-사용자 매핑 테이블")),
    MEMBER_IMG_NOT_FOUND(NOT_FOUND, "MEMBER_IMG404", entityNotFoundMessage("사용자-S3 매핑 테이블")),;

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

    private static String entityNoAuthorityMessage(String entityName) {
        return String.format(NO_AUTHORITIES, entityName);
    }
}
