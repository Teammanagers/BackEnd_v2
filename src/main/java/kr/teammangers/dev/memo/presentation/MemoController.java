package kr.teammangers.dev.memo.presentation;

import kr.teammangers.dev.auth.dto.AuthInfo;
import kr.teammangers.dev.common.payload.ApiRes;
import kr.teammangers.dev.memo.application.MemoCrudService;
import kr.teammangers.dev.memo.dto.req.CreateMemoReq;
import kr.teammangers.dev.memo.dto.req.DeleteMemoReq;
import kr.teammangers.dev.memo.dto.req.FixMemoReq;
import kr.teammangers.dev.memo.dto.req.UpdateMemoReq;
import kr.teammangers.dev.memo.dto.res.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/memo")
public class MemoController {

    private final MemoCrudService memoCrudService;

    @PostMapping
    public ApiRes<CreateMemoRes> createMemo(
            @RequestBody final CreateMemoReq req
    ) {
        CreateMemoRes result = memoCrudService.createMemo(req);
        return ApiRes.onSuccess(result);
    }

    @GetMapping("/list")
    public ApiRes<List<GetMemoRes>> getMemoListByFolder(
            @RequestParam("folderId") final Long folderId
    ) {
        List<GetMemoRes> result = memoCrudService.getMemoList(folderId);
        return ApiRes.onSuccess(result);
    }

    @PatchMapping
    public ApiRes<UpdateMemoRes> updateMemo(
            @AuthenticationPrincipal final AuthInfo auth,
            @RequestBody final UpdateMemoReq req
    ) {
        UpdateMemoRes result = memoCrudService.updateMemo(auth.memberDto().id(), req);
        return ApiRes.onSuccess(result);
    }

    @PatchMapping("/fixing")
    public ApiRes<FixMemoRes> fixMemo(
            @RequestBody final FixMemoReq req
    ) {
        FixMemoRes result = memoCrudService.fixMemo(req);
        return ApiRes.onSuccess(result);
    }

    @DeleteMapping
    public ApiRes<DeleteMemoRes> deleteMemo(
            @AuthenticationPrincipal final AuthInfo auth,
            @RequestBody final DeleteMemoReq req
    ) {
        DeleteMemoRes result = memoCrudService.deleteMemo(auth.memberDto().id(), req);
        return ApiRes.onSuccess(result);
    }

}
