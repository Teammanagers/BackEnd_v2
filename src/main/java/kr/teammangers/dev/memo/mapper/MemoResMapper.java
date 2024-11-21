package kr.teammangers.dev.memo.mapper;

import kr.teammangers.dev.memo.dto.MemoDto;
import kr.teammangers.dev.memo.dto.res.CreateMemoRes;
import kr.teammangers.dev.memo.dto.res.GetMemoRes;
import kr.teammangers.dev.tag.dto.TagDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemoResMapper {

    MemoResMapper MEMO_RES_MAPPER = Mappers.getMapper(MemoResMapper.class);

    @Mapping(target = "createdMemoId", source = "id")
    CreateMemoRes toCreate(MemoDto memoDto);

    @Mapping(target = "title", source = "memoDto.title")
    @Mapping(target = "content", source = "memoDto.content")
    @Mapping(target = "memoTagList", source = "memoTagList")
    GetMemoRes toGet(MemoDto memoDto, List<TagDto> memoTagList);

}
