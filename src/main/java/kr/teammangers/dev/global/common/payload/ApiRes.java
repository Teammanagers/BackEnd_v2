package kr.teammangers.dev.global.common.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import kr.teammangers.dev.global.common.payload.code.base.BaseCode;
import kr.teammangers.dev.global.common.payload.code.dto.enums.SuccessStatus;
import lombok.Builder;

@Builder
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public record ApiRes<T>(
        @JsonProperty("isSuccess")
        Boolean isSuccess,
        String code,
        String message,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        T result
) {

    // Success
    public static <T> ApiRes<T> onSuccess(final T result) {
        return ApiRes.<T>builder()
                .isSuccess(true)
                .code(SuccessStatus._OK.getCode())
                .message(SuccessStatus._OK.getMessage())
                .result(result)
                .build();

    }

    // Success: No result
    public static <T> ApiRes<T> onSuccess() {
        return ApiRes.<T>builder()
                .isSuccess(true)
                .code(SuccessStatus._OK.getCode())
                .message(SuccessStatus._OK.getMessage())
                .build();
    }

    public static <T> ApiRes<T> onSuccess(final BaseCode code, final T result) {
        return ApiRes.<T>builder()
                .isSuccess(true)
                .code(code.getReason().code())
                .message(SuccessStatus._OK.getMessage())
                .result(result)
                .build();
    }

    // Error
    public static <T> ApiRes<T> onFailure(final String code, final String message, final T data) {
        return ApiRes.<T>builder()
                .isSuccess(false)
                .code(code)
                .message(message)
                .result(data)
                .build();
    }
}
