package kr.teammangers.dev.memo.mapper;

import kr.teammangers.dev.memo.dto.FolderDto;
import kr.teammangers.dev.memo.dto.res.CreateFolderRes;
import kr.teammangers.dev.memo.dto.res.DeleteFolderRes;
import kr.teammangers.dev.memo.dto.res.GetFolderRes;
import kr.teammangers.dev.memo.dto.res.UpdateFolderRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FolderResMapper {

    FolderResMapper FOLDER_RES_MAPPER = Mappers.getMapper(FolderResMapper.class);

    @Mapping(target = "folderDto", source = "folderDto")
    GetFolderRes toGet(FolderDto folderDto);

    @Mapping(target = "createdFolderId", source = "folderDto.id")
    CreateFolderRes toCreate(FolderDto folderDto);

    @Mapping(target = "updatedFolderId", source = "folderDto.id")
    UpdateFolderRes toUpdate(FolderDto folderDto);

    @Mapping(target = "deletedFolderId", source = "folderId")
    DeleteFolderRes toDelete(Long folderId);

}
