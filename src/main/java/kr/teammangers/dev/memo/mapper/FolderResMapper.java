package kr.teammangers.dev.memo.mapper;

import kr.teammangers.dev.memo.dto.FolderDto;
import kr.teammangers.dev.memo.dto.response.GetFolderRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FolderResMapper {

    FolderResMapper FOLDER_RES_MAPPER = Mappers.getMapper(FolderResMapper.class);

    @Mapping(target = "folderDto", source = "folderDto")
    GetFolderRes toGet(FolderDto folderDto);

}
