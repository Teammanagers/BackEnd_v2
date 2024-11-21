package kr.teammangers.dev.memo.mapper;

import kr.teammangers.dev.memo.dto.MemoDto;
import kr.teammangers.dev.memo.dto.res.CreateMemoRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemoResMapper {

    MemoResMapper MEMO_RES_MAPPER = Mappers.getMapper(MemoResMapper.class);

    @Mapping(target = "createdMemoId", source = "id")
    CreateMemoRes toCreate(MemoDto memoDto);

}
