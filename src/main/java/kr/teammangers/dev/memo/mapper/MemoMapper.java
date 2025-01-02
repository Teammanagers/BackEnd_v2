package kr.teammangers.dev.memo.mapper;

import kr.teammangers.dev.memo.domain.entity.Folder;
import kr.teammangers.dev.memo.domain.entity.Memo;
import kr.teammangers.dev.memo.dto.MemoDto;
import kr.teammangers.dev.memo.dto.request.CreateMemoReq;
import kr.teammangers.dev.team.domain.entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemoMapper {

    MemoMapper MEMO_MAPPER = Mappers.getMapper(MemoMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "title", source = "req.title")
    @Mapping(target = "isFixed", expression = "java(false)")
    @Mapping(target = "folder", source = "folder")
    @Mapping(target = "team", source = "team")
    Memo toEntity(CreateMemoReq req, Folder folder, Team team);

    @Mapping(target = "folderId", source = "folder.id")
    @Mapping(target = "teamId", source = "team.id")
    MemoDto toDto(Memo memo);

}
