package kr.teammangers.dev.memo.mapper;

import kr.teammangers.dev.memo.domain.Folder;
import kr.teammangers.dev.memo.domain.Memo;
import kr.teammangers.dev.memo.dto.MemoDto;
import kr.teammangers.dev.memo.dto.req.CreateMemoReq;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemoMapper {

    MemoMapper MEMO_MAPPER = Mappers.getMapper(MemoMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "folder", source = "folder")
    Memo toEntity(CreateMemoReq req, Folder folder);

    @Mapping(target = "folderId", source = "folder.id")
    MemoDto toDto(Memo memo);

}
