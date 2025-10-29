package kr.teammangers.dev.global.error.code;

import kr.teammangers.dev.global.error.dto.ReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    // 일반적인 응답
    _OK(HttpStatus.OK, "200", "성공입니다."),
    _ACCEPTED(HttpStatus.ACCEPTED, "202", "추가 정보가 필요합니다."),
    
    // 인증 관련
    _LOGOUT_SUCCESS(HttpStatus.OK, "200", "로그아웃이 완료되었습니다."),
    _WITHDRAW_SUCCESS(HttpStatus.OK, "200", "회원탈퇴가 완료되었습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDto getReason() {
        return ReasonDto.builder()
                .httpStatus(this.httpStatus)
                .isSuccess(true)
                .code(this.code)
                .message(this.message)
                .build();
    }

    @Override
    public ReasonDto getReasonHttpStatus() {
        return ReasonDto.builder()
                .httpStatus(this.httpStatus)
                .isSuccess(true)
                .code(this.code)
                .message(this.message)
                .build();
    }
}
