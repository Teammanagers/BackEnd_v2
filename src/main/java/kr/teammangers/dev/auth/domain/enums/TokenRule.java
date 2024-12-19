package kr.teammangers.dev.auth.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenRule {
    TOKEN_ISSUE_HEADER("Set-Cookie"),
    TOKEN_RESOLVE_HEADER("Cookie"),
    ACCESS_PREFIX("Authorization"),
    REFRESH_PREFIX("Authorization-refresh"),;

    private final String value;
}
