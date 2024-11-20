package kr.teammangers.dev.auth.constants;

public final class AuthErrorMessage {

    private AuthErrorMessage() {}

    // OAuth2
    public static final String OAUTH2_AUTHENTICATION_FAILED = "OAuth2 로그인에 실패하였습니다.";
    public static final String SOCIAL_LOGIN_FAILED = "소셜 로그인에 실패하였습니다.";

    // Token
    public static final String INVALID_EXPIRED_TOKEN = "토큰이 유효하지 않거나, 만료된 토큰입니다.";

}
