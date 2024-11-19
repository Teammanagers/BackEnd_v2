package kr.teammangers.dev.common.payload.exception;

import kr.teammangers.dev.common.payload.code.base.ErrorBaseCode;
import kr.teammangers.dev.common.payload.code.dto.ErrorReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private final ErrorBaseCode code;

    public ErrorReasonDto getErrorReason() {
        return this.code.getReason();
    }

    public ErrorReasonDto getErrorReasonHttpStatus() {
        return this.code.getReasonHttpStatus();
    }
}
