package kr.teammangers.dev.auth.dto;

import kr.teammangers.dev.auth.domain.ProviderInfo;
import lombok.Builder;

@Builder
public record OAuth2UserInfo(
        String name,
        String email,
        String profile,
        ProviderInfo providerInfo,
        String birth,
        String telNum
) {
}
