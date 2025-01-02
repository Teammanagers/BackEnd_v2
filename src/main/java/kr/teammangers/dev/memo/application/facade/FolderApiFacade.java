package kr.teammangers.dev.memo.application.facade;

import kr.teammangers.dev.memo.application.service.FolderService;
import kr.teammangers.dev.memo.dto.FolderDto;
import kr.teammangers.dev.memo.dto.request.CreateFolderReq;
import kr.teammangers.dev.memo.dto.request.UpdateFolderReq;
import kr.teammangers.dev.memo.dto.response.GetFolderRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.teammangers.dev.memo.mapper.FolderMapper.FOLDER_MAPPER;
import static kr.teammangers.dev.memo.mapper.FolderResMapper.FOLDER_RES_MAPPER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FolderApiFacade {

    private final FolderService folderService;

    @Transactional
    public FolderDto createFolder(Long parentId, CreateFolderReq req) {
        Integer parentDepth = folderService.findDtoById(parentId).depth();
        return FOLDER_MAPPER.toDto(req, parentId, parentDepth + 1);
    }

    public GetFolderRes getRootFolderByTeamId(Long teamId) {
        FolderDto folderDto = folderService.findDtoByTeamId(teamId);
        return FOLDER_RES_MAPPER.toGet(folderDto);
    }

    public List<GetFolderRes> getFolderList(Long folderId) {
        return folderService.findAllDtoByParentId(folderId).stream()
                .map(FOLDER_RES_MAPPER::toGet)
                .toList();
    }

    @Transactional
    public FolderDto updateFolder(Long folderId, UpdateFolderReq req) {
        return folderService.update(folderId, req);
    }

    @Transactional
    public Long deleteFolder(Long folderId) {
        folderService.deleteAllByFolderId(folderId);
        return folderId;
    }

}
