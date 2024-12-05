package kr.teammangers.dev.memo.mapper;

import kr.teammangers.dev.memo.domain.Folder;
import kr.teammangers.dev.memo.dto.FolderDto;
import kr.teammangers.dev.memo.dto.req.CreateFolderReq;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FolderMapper {

    FolderMapper FOLDER_MAPPER = Mappers.getMapper(FolderMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "folderDto.name")
    @Mapping(target = "depth", source = "folderDto.depth")
    @Mapping(target = "parent", source = "parent")
    Folder toEntity(FolderDto folderDto, Folder parent);

    @Mapping(target = "parentId", source = "parent.id")
    FolderDto toDto(Folder folder);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "depth", source = "depth")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "useYn", ignore = true)
    FolderDto toDto(CreateFolderReq req, Integer depth);

}
