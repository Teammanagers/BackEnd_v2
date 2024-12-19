package kr.teammangers.dev.auth.infrastructure.oauth;

import kr.teammangers.dev.member.domain.embed.ProviderInfo;
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
