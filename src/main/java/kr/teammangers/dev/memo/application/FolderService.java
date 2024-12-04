package kr.teammangers.dev.memo.application;

import kr.teammangers.dev.memo.dto.FolderDto;
import kr.teammangers.dev.memo.dto.req.UpdateFolderReq;

import java.util.List;

public interface FolderService {
    FolderDto save(FolderDto folderDto);

    List<FolderDto> findAllDtoByParentId(Long parentId);

    FolderDto findDtoById(Long folderId);

    FolderDto update(UpdateFolderReq req);

    void deleteAllByFolderId(Long folderId);
}
