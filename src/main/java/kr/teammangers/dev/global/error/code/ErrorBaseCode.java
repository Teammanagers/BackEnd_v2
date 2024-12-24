package kr.teammangers.dev.global.error.code;

import kr.teammangers.dev.global.error.dto.ErrorReasonDto;

public interface ErrorBaseCode {
    ErrorReasonDto getReason();

    ErrorReasonDto getReasonHttpStatus();
}
