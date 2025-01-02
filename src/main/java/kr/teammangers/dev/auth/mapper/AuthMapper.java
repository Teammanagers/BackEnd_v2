package kr.teammangers.dev.auth.mapper;

import kr.teammangers.dev.auth.infrastructure.security.AuthInfo;
import kr.teammangers.dev.auth.dto.response.TokenRes;
import kr.teammangers.dev.member.dto.MemberDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Mapper(componentModel = "spring", imports = {Collections.class})
public interface AuthMapper {

    AuthMapper AUTH_MAPPER = Mappers.getMapper(AuthMapper.class);

    @Mapping(target = "memberDto", source = "memberDto")
    @Mapping(target = "attributes", source = "oAuth2UserAttributes")
    @Mapping(target = "attributeKey", source = "userNameAttributeName")
    @Mapping(target = "isNewMember", source = "isNewMember")
    AuthInfo toAuthInfo(MemberDto memberDto, Map<String, Object> oAuth2UserAttributes, String userNameAttributeName, boolean isNewMember);

    @Mapping(target = "memberDto", source = "memberDto")
    @Mapping(target = "attributes", expression = "java(Collections.emptyMap())")
    @Mapping(target = "attributeKey", constant = "null") // 기본값 설정
    @Mapping(target = "isNewMember", constant = "false")    // 기본값 설정
    AuthInfo toAuthInfo(MemberDto memberDto, Collection<? extends GrantedAuthority> authorities);

    @Mapping(target = "accessToken", source = "accessToken")
    @Mapping(target = "refreshToken", source = "refreshToken")
    TokenRes toTokenRes(String accessToken, String refreshToken);
}
