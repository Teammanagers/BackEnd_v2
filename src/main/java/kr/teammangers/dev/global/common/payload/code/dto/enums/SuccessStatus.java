package kr.teammangers.dev.global.common.payload.code.dto.enums;

import kr.teammangers.dev.global.common.payload.code.base.BaseCode;
import kr.teammangers.dev.global.common.payload.code.dto.ReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    // 일반적인 응답
    _OK(HttpStatus.OK, "200", "성공입니다."),
    _ACCEPTED(HttpStatus.ACCEPTED, "202", "추가 정보가 필요합니다.")
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDto getReason() {
        return null;
    }

    @Override
    public ReasonDto getReasonHttpStatus() {
        return null;
    }
}
