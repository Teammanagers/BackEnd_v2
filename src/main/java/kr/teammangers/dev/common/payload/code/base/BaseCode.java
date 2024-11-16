package kr.teammangers.dev.common.payload.code.base;

import kr.teammangers.dev.common.payload.code.dto.ReasonDto;

public interface BaseCode {
    ReasonDto getReason();

    ReasonDto getReasonHttpStatus();
}
