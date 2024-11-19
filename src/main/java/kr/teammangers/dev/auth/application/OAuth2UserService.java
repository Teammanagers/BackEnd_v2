package kr.teammangers.dev.auth.application;

import kr.teammangers.dev.auth.dto.OAuth2UserInfo;
import kr.teammangers.dev.auth.mapper.AuthMapper;
import kr.teammangers.dev.member.application.MemberService;
import kr.teammangers.dev.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

import static kr.teammangers.dev.auth.constants.AuthConstant.SEC_BY_JUDGE_NEW_MEMBER;
import static kr.teammangers.dev.auth.mapper.ProviderMapper.PROVIDER_MAPPER;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final MemberService memberService;

    @Override
    @Transactional
    public OAuth2User loadUser(final OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Map<String, Object> attributes = super.loadUser(userRequest).getAttributes();
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuth2UserInfo oAuth2UserInfo = PROVIDER_MAPPER.oAuth2UerInfoOf(registrationId, attributes);

        MemberDto memberDto = memberService.findDtoOrSave(oAuth2UserInfo);

        return AuthMapper.AUTH_MAPPER.toAuthInfo(memberDto, attributes, userNameAttributeName, isNewMember(memberDto));

    }

    private boolean isNewMember(final MemberDto memberDto) {
        return memberDto.createdAt() != null && memberDto.createdAt().isAfter(LocalDateTime.now().minusSeconds(SEC_BY_JUDGE_NEW_MEMBER));
    }
}
