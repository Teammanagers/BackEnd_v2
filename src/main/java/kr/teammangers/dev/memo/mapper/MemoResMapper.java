package kr.teammangers.dev.memo.mapper;

import kr.teammangers.dev.memo.dto.MemoDto;
import kr.teammangers.dev.memo.dto.res.CreateMemoRes;
import kr.teammangers.dev.memo.dto.res.DeleteMemoRes;
import kr.teammangers.dev.memo.dto.res.GetMemoRes;
import kr.teammangers.dev.memo.dto.res.UpdateMemoRes;
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

    @Mapping(target = "deletedMemoId", source = "memoId")
    DeleteMemoRes toDelete(Long memoId);

}
