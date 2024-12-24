package kr.teammangers.dev.auth.application.service;

import kr.teammangers.dev.auth.infrastructure.oauth.OAuth2UserInfo;
import kr.teammangers.dev.member.application.service.MemberService;
import kr.teammangers.dev.member.dto.MemberDto;
import kr.teammangers.dev.member.domain.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static kr.teammangers.dev.auth.constant.AuthConstant.SEC_BY_JUDGE_NEW_MEMBER;
import static kr.teammangers.dev.auth.mapper.AuthMapper.AUTH_MAPPER;
import static kr.teammangers.dev.auth.mapper.ProviderMapper.PROVIDER_MAPPER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService implements UserDetailsService {

    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        MemberDto memberDto = memberService.findDtoById(Long.parseLong(username));

        Collection<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(Role.USER.getValue())
        );

        return AUTH_MAPPER.toAuthInfo(memberDto, authorities);
    }

    @Transactional
    public OAuth2User loadOAuth2User(final OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        Map<String, Object> attributes = delegate.loadUser(userRequest).getAttributes();

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuth2UserInfo oAuth2UserInfo = PROVIDER_MAPPER.oAuth2UerInfoOf(registrationId, attributes);

        MemberDto memberDto = memberService.findDtoOrSave(oAuth2UserInfo);

        return AUTH_MAPPER.toAuthInfo(
                memberDto,
                attributes,
                userNameAttributeName,
                isNewMember(memberDto)
        );
    }

    private boolean isNewMember(final MemberDto memberDto) {
        return memberDto.createdAt() != null &&
                memberDto.createdAt().isAfter(LocalDateTime.now()
                        .minusSeconds(SEC_BY_JUDGE_NEW_MEMBER));
    }
}
