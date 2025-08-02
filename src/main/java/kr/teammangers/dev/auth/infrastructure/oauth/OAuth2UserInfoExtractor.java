package kr.teammangers.dev.auth.infrastructure.oauth;

import kr.teammangers.dev.member.domain.embed.ProviderInfo;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class OAuth2UserInfoExtractor {

    public static OAuth2UserInfo extract(OAuth2User oauth2User, String registrationId) {
        return switch (registrationId.toLowerCase()) {
            case "naver" -> extractNaverUser(oauth2User);
            case "google" -> extractGoogleUser(oauth2User);
            case "kakao" -> extractKakaoUser(oauth2User);
            default -> throw new IllegalArgumentException("지원하지 않는 소셜 로그인: " + registrationId);
        };
    }

    private static OAuth2UserInfo extractNaverUser(OAuth2User oauth2User) {
        Map<String, Object> response = (Map<String, Object>) oauth2User.getAttributes().get("response");

        return OAuth2UserInfo.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .profile((String) response.get("profile_image"))
                .birth((String) response.get("birthday"))
                .telNum((String) response.get("mobile"))
                .providerInfo(ProviderInfo.builder()
                        .provider("naver")
                        .providerId((String) response.get("id"))
                        .build())
                .build();
    }

    private static OAuth2UserInfo extractGoogleUser(OAuth2User oauth2User) {
        Map<String, Object> attributes = oauth2User.getAttributes();

        return OAuth2UserInfo.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .profile((String) attributes.get("picture"))
                .providerInfo(ProviderInfo.builder()
                        .provider("google")
                        .providerId((String) attributes.get("sub"))
                        .build())
                .build();
    }

    private static OAuth2UserInfo extractKakaoUser(OAuth2User oauth2User) {
        Map<String, Object> attributes = oauth2User.getAttributes();
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuth2UserInfo.builder()
                .name((String) profile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .profile((String) profile.get("profile_image_url"))
                .providerInfo(ProviderInfo.builder()
                        .provider("kakao")
                        .providerId(attributes.get("id").toString())
                        .build())
                .build();
    }
}
