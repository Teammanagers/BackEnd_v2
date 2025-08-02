package kr.teammangers.dev.auth.dto.response;

public record TokenRes(
        boolean isNewUser,
        String accessToken,
        String refreshToken
) {
}
