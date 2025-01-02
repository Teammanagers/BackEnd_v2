package kr.teammangers.dev.tag.mapper;

import kr.teammangers.dev.tag.domain.entity.Tag;
import kr.teammangers.dev.tag.domain.enums.TagType;
import kr.teammangers.dev.tag.dto.TagDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagMapper TAG_MAPPER = Mappers.getMapper(TagMapper.class);

    TagDto toDto(Tag tag);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "tagName")
    Tag toEntity(String tagName, TagType type);

}
