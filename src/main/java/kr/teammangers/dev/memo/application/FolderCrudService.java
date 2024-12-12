package kr.teammangers.dev.memo.application;

import kr.teammangers.dev.memo.dto.FolderDto;
import kr.teammangers.dev.memo.dto.req.CreateFolderReq;
import kr.teammangers.dev.memo.dto.req.DeleteFolderReq;
import kr.teammangers.dev.memo.dto.req.UpdateFolderReq;
import kr.teammangers.dev.memo.dto.res.CreateFolderRes;
import kr.teammangers.dev.memo.dto.res.DeleteFolderRes;
import kr.teammangers.dev.memo.dto.res.GetFolderRes;
import kr.teammangers.dev.memo.dto.res.UpdateFolderRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.teammangers.dev.memo.mapper.FolderMapper.FOLDER_MAPPER;
import static kr.teammangers.dev.memo.mapper.FolderResMapper.FOLDER_RES_MAPPER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FolderCrudService {

    private final FolderService folderService;

    @Transactional
    public CreateFolderRes createFolder(CreateFolderReq req) {
        Integer parentDepth = folderService.findDtoById(req.parentId()).depth();
        FolderDto folderDto = FOLDER_MAPPER.toDto(req, parentDepth + 1);

        return FOLDER_RES_MAPPER.toCreate(folderService.save(folderDto));
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
    public UpdateFolderRes updateFolder(UpdateFolderReq req) {
        FolderDto folderDto = folderService.update(req);
        return FOLDER_RES_MAPPER.toUpdate(folderDto);
    }

    @Transactional
    public DeleteFolderRes deleteFolder(DeleteFolderReq req) {
        folderService.deleteAllByFolderId(req.folderId());
        return FOLDER_RES_MAPPER.toDelete(req.folderId());
    }

}
