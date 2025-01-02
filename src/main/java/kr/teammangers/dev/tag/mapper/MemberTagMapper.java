package kr.teammangers.dev.tag.mapper;

import kr.teammangers.dev.member.domain.entity.Member;
import kr.teammangers.dev.tag.domain.entity.MemberTag;
import kr.teammangers.dev.tag.domain.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemberTagMapper {

    MemberTagMapper MEMBER_TAG_MAPPER = Mappers.getMapper(MemberTagMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "member", source = "member")
    @Mapping(target = "tag", source = "tag")
    MemberTag toEntity(Member member, Tag tag);

}
