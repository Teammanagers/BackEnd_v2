package kr.teammangers.dev.auth.application.service;

import kr.teammangers.dev.auth.infrastructure.oauth.OAuth2UserInfo;
import kr.teammangers.dev.auth.infrastructure.oauth.OAuth2UserInfoExtractor;
import kr.teammangers.dev.auth.infrastructure.security.AuthInfo;
import kr.teammangers.dev.member.domain.entity.Member;
import kr.teammangers.dev.member.domain.enums.Role;
import kr.teammangers.dev.member.dto.MemberDto;
import kr.teammangers.dev.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 소셜 플랫폼별 사용자 정보 추출
        OAuth2UserInfo userInfo = OAuth2UserInfoExtractor.extract(oauth2User, registrationId);

        // providerId로 기존 사용자 확인
        Optional<Member> existingMember = memberRepository.findByProviderInfo_ProviderId(
                userInfo.providerInfo().getProviderId()
        );

        boolean isNewMember;
        Member member;

        if (existingMember.isPresent()) {
            // 기존 사용자
            member = existingMember.get();
            isNewMember = false;
            log.info("기존 사용자 로그인: {}, provider: {}",
                    member.getName(), userInfo.providerInfo().getProvider());
        } else {
            // 신규 사용자 - 회원가입 처리
            member = createNewMember(userInfo);
            memberRepository.save(member);
            isNewMember = true;
            log.info("신규 사용자 가입: {}, provider: {}",
                    member.getName(), userInfo.providerInfo().getProvider());
        }

        // MemberDto 생성
        MemberDto memberDto = MemberDto.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .birth(member.getBirth())
                .telNum(member.getTelNum())
                .belong(member.getBelong())
                .role(member.getRole())
                .providerInfo(member.getProviderInfo())
                .build();

        return AuthInfo.builder()
                .memberDto(memberDto)
                .attributes(oauth2User.getAttributes())
                .attributeKey(getAttributeKey(registrationId))
                .isNewMember(isNewMember)
                .build();
    }

    private Member createNewMember(OAuth2UserInfo userInfo) {
        return Member.builder()
                .name(userInfo.name())
                .email(userInfo.email())
                .birth(userInfo.birth())
                .telNum(userInfo.telNum())
                .providerInfo(userInfo.providerInfo())
                .role(Role.USER)
                .build();
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
