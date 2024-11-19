package kr.teammangers.dev.auth.mapper;

import kr.teammangers.dev.auth.domain.embed.ProviderInfo;
import kr.teammangers.dev.auth.dto.OAuth2UserInfo;
import kr.teammangers.dev.common.payload.code.dto.enums.ErrorStatus;
import kr.teammangers.dev.common.payload.exception.GeneralException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Map;

import static kr.teammangers.dev.auth.constants.AttributeConstant.*;
import static kr.teammangers.dev.auth.constants.ProviderConstant.*;

@Mapper(componentModel = "spring")
public interface ProviderMapper {

    ProviderMapper PROVIDER_MAPPER = Mappers.getMapper(ProviderMapper.class);

    default OAuth2UserInfo oAuth2UerInfoOf(final String registrationId, final Map<String, Object> attributes) {
        return switch (registrationId) {
            case GOOGLE -> ofGoogle(attributes);
            case KAKAO -> ofKakao(attributes);
            case NAVER -> ofNaver(attributes);
            default -> throw new GeneralException(ErrorStatus.AUTH_ILLEGAL_REGISTRATION_ID);
        };
    }

    @Mapping(target = "provider", source = "provider")
    @Mapping(target = "providerId", source = "providerId")
    ProviderInfo ofProviderInfo(String provider, String providerId);

    default OAuth2UserInfo ofGoogle(final Map<String, Object> attributes) {
        String email = (String) attributes.get(GOOGLE_EMAIL);
        return OAuth2UserInfo.builder()
                .providerInfo(ofProviderInfo(GOOGLE, (String) attributes.get(GOOGLE_PROVIDER_ID)))
                .name((email.split("@")[0]))
                .email(email)
                .build();
    }

    default OAuth2UserInfo ofKakao(final Map<String, Object> attributes) {
        Map<String, Object> info = (Map<String, Object>) ((Map<String, Object>) attributes.get(KAKAO_ACCOUNT)).get(KAKAO_INFO);
        return OAuth2UserInfo.builder()
                .providerInfo(ofProviderInfo(KAKAO, String.valueOf(attributes.get(KAKAO_PROVIDER_ID))))
                .name((String) info.get(KAKAO_NAME))
                .profile((String) info.get(KAKAO_PROFILE))
                .build();
    }

    default OAuth2UserInfo ofNaver(final Map<String, Object> attributes) {
        Map<String, Object> info = (Map<String, Object>) attributes.get(NAVER_INFO);
        return OAuth2UserInfo.builder()
                .providerInfo(ofProviderInfo(NAVER, (String) info.get(NAVER_PROVIDER_ID)))
                .name((String) info.get(NAVER_NAME))
                .email((String) info.get(NAVER_EMAIL))
                .profile((String) info.get(NAVER_PROFILE))
                .birth((String) info.get(NAVER_BIRTH))
                .telNum((String) info.get(NAVER_TEL_NUM))
                .build();
    }
}
