package kr.teammangers.dev.common.payload.code.base;

import kr.teammangers.dev.common.payload.code.dto.ErrorReasonDto;

public interface ErrorBaseCode {
    ErrorReasonDto getReason();

    ErrorReasonDto getReasonHttpStatus();
}
