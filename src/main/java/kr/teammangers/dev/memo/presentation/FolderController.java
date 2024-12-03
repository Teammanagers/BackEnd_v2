package kr.teammangers.dev.memo.presentation;

import kr.teammangers.dev.auth.dto.AuthInfo;
import kr.teammangers.dev.common.payload.ApiRes;
import kr.teammangers.dev.memo.application.FolderCrudService;
import kr.teammangers.dev.memo.dto.req.CreateFolderReq;
import kr.teammangers.dev.memo.dto.req.DeleteFolderReq;
import kr.teammangers.dev.memo.dto.req.UpdateFolderReq;
import kr.teammangers.dev.memo.dto.res.CreateFolderRes;
import kr.teammangers.dev.memo.dto.res.DeleteFolderRes;
import kr.teammangers.dev.memo.dto.res.GetFolderRes;
import kr.teammangers.dev.memo.dto.res.UpdateFolderRes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/folder")
public class FolderController {

    private final FolderCrudService folderCrudService;

    @PostMapping
    public ApiRes<CreateFolderRes> createFolder(
            @RequestBody final CreateFolderReq req
    ) {
        CreateFolderRes result = folderCrudService.createFolder(req);
        return ApiRes.onSuccess(result);
    }

    @GetMapping("/{folderId}/list")
    public ApiRes<List<GetFolderRes>> getFolderListByTeamId(
            @PathVariable("folderId") final Long folderId
    ) {
        List<GetFolderRes> result = folderCrudService.getFolderList(folderId);
        return ApiRes.onSuccess(result);
    }

    @PatchMapping
    public ApiRes<UpdateFolderRes> updateFolder(
            @AuthenticationPrincipal final AuthInfo auth,
            @RequestBody final UpdateFolderReq req
    ) {
        UpdateFolderRes result = folderCrudService.updateFolder(auth.memberDto().id(), req);
        return ApiRes.onSuccess(result);
    }

    @DeleteMapping
    public ApiRes<DeleteFolderRes> deleteFolder(
            @AuthenticationPrincipal final AuthInfo auth,
            @RequestBody final DeleteFolderReq req
    ) {
        DeleteFolderRes result = folderCrudService.deleteFolder(auth.memberDto().id(), req);
        return ApiRes.onSuccess(result);
    }

}
