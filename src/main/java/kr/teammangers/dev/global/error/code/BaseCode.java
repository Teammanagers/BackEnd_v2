package kr.teammangers.dev.global.error.code;

import kr.teammangers.dev.global.error.dto.ReasonDto;

public interface BaseCode {
    ReasonDto getReason();

    ReasonDto getReasonHttpStatus();
}
