package kr.teammangers.dev.auth.dto.response;

public record TokenRes(
        String accessToken,
        String refreshToken
) {
}
