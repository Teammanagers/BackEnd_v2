package kr.teammangers.dev.auth.application.service;

import kr.teammangers.dev.auth.infrastructure.oauth.OAuth2UserInfo;
import kr.teammangers.dev.auth.infrastructure.oauth.OAuth2UserInfoExtractor;
import kr.teammangers.dev.auth.infrastructure.security.AuthInfo;
import kr.teammangers.dev.member.application.service.MemberService;
import kr.teammangers.dev.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static kr.teammangers.dev.auth.constant.AuthConstant.SEC_BY_JUDGE_NEW_MEMBER;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberService memberService;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 소셜 플랫폼별 사용자 정보 추출
        OAuth2UserInfo userInfo = OAuth2UserInfoExtractor.extract(oauth2User, registrationId);

        // MemberService를 통한 회원 처리 (재가입 로직 포함)
        MemberDto memberDto = memberService.findDtoOrSave(userInfo);

        // 신규 회원 여부 판단
        boolean isNewMember = isNewMember(memberDto);

        log.info("{} 사용자 로그인: {}, provider: {}",
                isNewMember ? "신규" : "기존",
                memberDto.name(),
                userInfo.providerInfo().getProvider());

        return AuthInfo.builder()
                .memberDto(memberDto)
                .attributes(oauth2User.getAttributes())
                .attributeKey(getAttributeKey(registrationId))
                .isNewMember(isNewMember)
                .build();
    }

    private boolean isNewMember(final MemberDto memberDto) {
        return memberDto.createdAt() != null &&
                memberDto.createdAt().isAfter(LocalDateTime.now()
                        .minusSeconds(SEC_BY_JUDGE_NEW_MEMBER));
    }

    private String getAttributeKey(String registrationId) {
        return switch (registrationId.toLowerCase()) {
            case "naver" -> "response";
            case "google" -> "sub";
            case "kakao" -> "id";
            default -> "id";
        };
    }
}
