package kr.teammangers.dev.tag.mapper;

import kr.teammangers.dev.memo.domain.Memo;
import kr.teammangers.dev.tag.domain.Tag;
import kr.teammangers.dev.tag.domain.mapping.MemoTag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemoTagMapper {

    MemoTagMapper MEMO_TAG_MAPPER = Mappers.getMapper(MemoTagMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "memo", source = "memo")
    @Mapping(target = "tag", source = "tag")
    MemoTag toEntity(Memo memo, Tag tag);

}
