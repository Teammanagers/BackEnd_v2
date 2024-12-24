package kr.teammangers.dev.memo.presentation;

import kr.teammangers.dev.global.common.response.ApiRes;
import kr.teammangers.dev.memo.application.facade.FolderApiFacade;
import kr.teammangers.dev.memo.dto.request.CreateFolderReq;
import kr.teammangers.dev.memo.dto.request.DeleteFolderReq;
import kr.teammangers.dev.memo.dto.request.UpdateFolderReq;
import kr.teammangers.dev.memo.dto.response.CreateFolderRes;
import kr.teammangers.dev.memo.dto.response.DeleteFolderRes;
import kr.teammangers.dev.memo.dto.response.GetFolderRes;
import kr.teammangers.dev.memo.dto.response.UpdateFolderRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/folder")
public class FolderController {

    private final FolderApiFacade folderApiFacade;

    @PostMapping
    public ApiRes<CreateFolderRes> createFolder(
            @RequestBody final CreateFolderReq req
    ) {
        CreateFolderRes result = folderApiFacade.createFolder(req);
        return ApiRes.onSuccess(result);
    }

    @GetMapping("/root/{teamId}")
    public ApiRes<GetFolderRes> getRootFolderByTeamId(
            @PathVariable("teamId") final Long teamId
    ) {
        GetFolderRes result = folderApiFacade.getRootFolderByTeamId(teamId);
        return ApiRes.onSuccess(result);
    }

    @GetMapping("/{folderId}/list")
    public ApiRes<List<GetFolderRes>> getFolderListByFolderId(
            @PathVariable("folderId") final Long folderId
    ) {
        List<GetFolderRes> result = folderApiFacade.getFolderList(folderId);
        return ApiRes.onSuccess(result);
    }

    @PatchMapping
    public ApiRes<UpdateFolderRes> updateFolder(
            @RequestBody final UpdateFolderReq req
    ) {
        UpdateFolderRes result = folderApiFacade.updateFolder(req);
        return ApiRes.onSuccess(result);
    }

    @DeleteMapping
    public ApiRes<DeleteFolderRes> deleteFolder(
            @RequestBody final DeleteFolderReq req
    ) {
        DeleteFolderRes result = folderApiFacade.deleteFolder(req);
        return ApiRes.onSuccess(result);
    }

}
