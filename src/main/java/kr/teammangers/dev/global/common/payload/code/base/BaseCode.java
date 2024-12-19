package kr.teammangers.dev.global.common.payload.code.base;

import kr.teammangers.dev.global.common.payload.code.dto.ReasonDto;

public interface BaseCode {
    ReasonDto getReason();

    ReasonDto getReasonHttpStatus();
}
