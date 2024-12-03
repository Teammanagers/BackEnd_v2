package kr.teammangers.dev.memo.application.impl;

import kr.teammangers.dev.memo.application.FolderCrudService;
import kr.teammangers.dev.memo.application.FolderService;
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
public class FolderCrudServiceImpl implements FolderCrudService {

    private final FolderService folderService;

    @Override
    @Transactional
    public CreateFolderRes createFolder(CreateFolderReq req) {
        Integer parentDepth = folderService.findDtoById(req.parentId()).depth();
        FolderDto folderDto = FOLDER_MAPPER.toDto(req, parentDepth + 1);

        return FOLDER_RES_MAPPER.toCreate(folderService.save(folderDto));
    }

    @Override
    public List<GetFolderRes> getFolderList(Long folderId) {
        return folderService.findAllDtoByParentId(folderId).stream()
                .map(folderDto -> FOLDER_RES_MAPPER.toGet(folderId, folderDto))
                .toList();
    }

    @Override
    @Transactional
    public UpdateFolderRes updateFolder(Long memberId, UpdateFolderReq req) {
        FolderDto folderDto = folderService.update(req);
        return FOLDER_RES_MAPPER.toUpdate(folderDto);
    }

    @Override
    @Transactional
    public DeleteFolderRes deleteFolder(Long memberId, DeleteFolderReq req) {
        folderService.deleteAllByFolderId(req.folderId());
        return FOLDER_RES_MAPPER.toDelete(req.folderId());
    }

}
