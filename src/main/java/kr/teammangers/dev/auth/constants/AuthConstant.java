package kr.teammangers.dev.auth.constants;

public final class AuthConstant {

    private AuthConstant() {}

    // Token
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String ROLE_CLAIM = "role";
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String IS_NEW_MEMBER = "isNewMember";

    // Auth process
    public static final int SEC_BY_JUDGE_NEW_MEMBER = 3;

}
