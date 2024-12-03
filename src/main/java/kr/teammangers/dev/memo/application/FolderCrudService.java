package kr.teammangers.dev.memo.application;

import kr.teammangers.dev.memo.dto.req.CreateFolderReq;
import kr.teammangers.dev.memo.dto.req.DeleteFolderReq;
import kr.teammangers.dev.memo.dto.req.UpdateFolderReq;
import kr.teammangers.dev.memo.dto.res.CreateFolderRes;
import kr.teammangers.dev.memo.dto.res.DeleteFolderRes;
import kr.teammangers.dev.memo.dto.res.GetFolderRes;
import kr.teammangers.dev.memo.dto.res.UpdateFolderRes;

import java.util.List;

public interface FolderCrudService {

    CreateFolderRes createFolder(CreateFolderReq req);

    List<GetFolderRes> getFolderList(Long folderId);

    UpdateFolderRes updateFolder(Long memberId, UpdateFolderReq req);

    DeleteFolderRes deleteFolder(Long memberId, DeleteFolderReq req);
}
