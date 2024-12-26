package kr.teammangers.dev.memo.mapper;

import kr.teammangers.dev.memo.dto.MemoDto;
import kr.teammangers.dev.memo.dto.response.GetMemoRes;
import kr.teammangers.dev.tag.dto.TagDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemoResMapper {

    MemoResMapper MEMO_RES_MAPPER = Mappers.getMapper(MemoResMapper.class);

    @Mapping(target = "memoDto", source = "memoDto")
    @Mapping(target = "memoTagList", source = "memoTagList")
    GetMemoRes toGet(MemoDto memoDto, List<TagDto> memoTagList);

}
