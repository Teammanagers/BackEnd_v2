package kr.teammangers.dev.auth.dto.res;

public record TokenRes(
        String accessToken,
        String refreshToken
) {
}
