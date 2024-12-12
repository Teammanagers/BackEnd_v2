package kr.teammangers.dev.auth.application;

import kr.teammangers.dev.member.application.MemberService;
import kr.teammangers.dev.member.dto.MemberDto;
import kr.teammangers.dev.member.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;

import static kr.teammangers.dev.auth.mapper.AuthMapper.AUTH_MAPPER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        MemberDto memberDto = memberService.findDtoById(Long.parseLong(username));

        Collection<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(Role.USER.getValue())
        );

        return AUTH_MAPPER.toAuthInfo(memberDto, authorities);
    }
}
