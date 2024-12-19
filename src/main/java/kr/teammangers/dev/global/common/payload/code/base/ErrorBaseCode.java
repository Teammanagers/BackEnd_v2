package kr.teammangers.dev.global.common.payload.code.base;

import kr.teammangers.dev.global.common.payload.code.dto.ErrorReasonDto;

public interface ErrorBaseCode {
    ErrorReasonDto getReason();

    ErrorReasonDto getReasonHttpStatus();
}
