package kr.teammangers.dev.memo.mapper;

import kr.teammangers.dev.memo.dto.MemoDto;
import kr.teammangers.dev.memo.dto.response.*;
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

    @Mapping(target = "memoDto", source = "memoDto")
    @Mapping(target = "memoTagList", source = "memoTagList")
    GetMemoRes toGet(MemoDto memoDto, List<TagDto> memoTagList);

    @Mapping(target = "updatedMemoId", source = "id")
    UpdateMemoRes toUpdate(MemoDto memoDto);

    @Mapping(target = "memoId", source = "memoId")
    @Mapping(target = "isFixed", source = "isFixed")
    FixMemoRes toFix(Long memoId, Boolean isFixed);

    @Mapping(target = "deletedMemoId", source = "memoId")
    DeleteMemoRes toDelete(Long memoId);

}
