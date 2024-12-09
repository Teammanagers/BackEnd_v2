package kr.teammangers.dev.auth.dto;

import kr.teammangers.dev.member.dto.MemberDto;
import kr.teammangers.dev.member.enums.Role;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Builder
public record AuthInfo(
        MemberDto memberDto,
        Map<String, Object> attributes,
        String attributeKey,
        boolean isNewMember
) implements OAuth2User, UserDetails {

    @Override
    public String getName() {
        return attributes.get(attributeKey).toString();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return memberDto.providerInfo().getProviderId();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(Role.USER.getValue()));
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

}
