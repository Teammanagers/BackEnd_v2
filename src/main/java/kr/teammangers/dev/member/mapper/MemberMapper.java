package kr.teammangers.dev.member.mapper;

import kr.teammangers.dev.auth.infrastructure.oauth.OAuth2UserInfo;
import kr.teammangers.dev.member.domain.entity.Member;
import kr.teammangers.dev.member.dto.MemberDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    MemberMapper MEMBER_MAPPER = Mappers.getMapper(MemberMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "belong", ignore = true)
    @Mapping(target = "role", ignore = true)
    Member toEntity(OAuth2UserInfo oAuth2UserInfo);

    MemberDto toDto(Member member);

}
