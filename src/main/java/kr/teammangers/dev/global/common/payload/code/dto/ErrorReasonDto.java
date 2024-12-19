package kr.teammangers.dev.global.common.payload.code.dto;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ErrorReasonDto(
        HttpStatus httpStatus,
        boolean isSuccess,
        String code,
        String message
) {
}
