package kr.teammangers.dev.tag.mapper;

import kr.teammangers.dev.member.domain.Member;
import kr.teammangers.dev.tag.domain.Tag;
import kr.teammangers.dev.tag.domain.mapping.Major;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MajorMapper {

    MajorMapper MAJOR_MAPPER = Mappers.getMapper(MajorMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "member", source = "member")
    @Mapping(target = "tag", source = "tag")
    Major toEntity(Member member, Tag tag);

}
