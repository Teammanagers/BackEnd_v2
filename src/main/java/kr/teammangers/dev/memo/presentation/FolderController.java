package kr.teammangers.dev.memo.presentation;

import kr.teammangers.dev.global.common.response.ApiRes;
import kr.teammangers.dev.memo.application.facade.FolderApiFacade;
import kr.teammangers.dev.memo.dto.FolderDto;
import kr.teammangers.dev.memo.dto.request.CreateFolderReq;
import kr.teammangers.dev.memo.dto.request.UpdateFolderReq;
import kr.teammangers.dev.memo.dto.response.GetFolderRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/folder")
public class FolderController {

    private final FolderApiFacade folderApiFacade;

    @PostMapping("/{parentId}")
    public ApiRes<FolderDto> createFolder(
            @PathVariable("parentId") final Long parentId,
            @RequestBody final CreateFolderReq req
    ) {
        FolderDto result = folderApiFacade.createFolder(parentId, req);
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

    @PatchMapping("/{folderId}")
    public ApiRes<FolderDto> updateFolder(
            @PathVariable("folderId") final Long folderId,
            @RequestBody final UpdateFolderReq req
    ) {
        FolderDto result = folderApiFacade.updateFolder(folderId, req);
        return ApiRes.onSuccess(result);
    }

    @DeleteMapping("/{folderId}")
    public ApiRes<Long> deleteFolder(
            @PathVariable("folderId") final Long folderId
    ) {
        Long result = folderApiFacade.deleteFolder(folderId);
        return ApiRes.onSuccess(result);
    }

}
