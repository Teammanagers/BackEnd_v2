package kr.teammangers.dev.common.payload.code.dto.enums;

public final class CommonErrorMessage {

    private CommonErrorMessage() {}

    // 공통 에러 응답 템플릿
    public static final String NOT_FOUND_TEMPLATE = "%s을(를) 찾을 수 없습니다.";
    public static final String IS_ALREADY_EXISTS = "이미 등록되어 있습니다.";

}
