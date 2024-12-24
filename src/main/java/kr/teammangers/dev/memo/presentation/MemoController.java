package kr.teammangers.dev.memo.presentation;

import kr.teammangers.dev.auth.infrastructure.security.AuthInfo;
import kr.teammangers.dev.global.common.response.ApiRes;
import kr.teammangers.dev.memo.application.facade.MemoApiFacade;
import kr.teammangers.dev.memo.dto.request.CreateMemoReq;
import kr.teammangers.dev.memo.dto.request.DeleteMemoReq;
import kr.teammangers.dev.memo.dto.request.FixMemoReq;
import kr.teammangers.dev.memo.dto.request.UpdateMemoReq;
import kr.teammangers.dev.memo.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/memo")
public class MemoController {

    private final MemoApiFacade memoApiFacade;

    @PostMapping
    public ApiRes<CreateMemoRes> createMemo(
            @RequestBody final CreateMemoReq req
    ) {
        CreateMemoRes result = memoApiFacade.createMemo(req);
        return ApiRes.onSuccess(result);
    }

    @GetMapping("/list")
    public ApiRes<List<GetMemoRes>> getMemoListByFolder(
            @RequestParam("folderId") final Long folderId,
            @RequestParam(value = "isFixed", required = false) final Boolean isFixed
    ) {
        List<GetMemoRes> result = memoApiFacade.getMemoList(folderId, isFixed);
        return ApiRes.onSuccess(result);
    }

    @GetMapping("/fixed")
    public ApiRes<List<GetMemoRes>> getMemoFixed(
            @RequestParam("teamId") final Long teamId
    ) {
        List<GetMemoRes> result = memoApiFacade.getMemoListByFixed(teamId);
        return ApiRes.onSuccess(result);
    }

    @PatchMapping
    public ApiRes<UpdateMemoRes> updateMemo(
            @AuthenticationPrincipal final AuthInfo auth,
            @RequestBody final UpdateMemoReq req
    ) {
        UpdateMemoRes result = memoApiFacade.updateMemo(auth.memberDto().id(), req);
        return ApiRes.onSuccess(result);
    }

    @PatchMapping("/fixing")
    public ApiRes<FixMemoRes> fixMemo(
            @RequestBody final FixMemoReq req
    ) {
        FixMemoRes result = memoApiFacade.fixMemo(req);
        return ApiRes.onSuccess(result);
    }

    @DeleteMapping
    public ApiRes<DeleteMemoRes> deleteMemo(
            @AuthenticationPrincipal final AuthInfo auth,
            @RequestBody final DeleteMemoReq req
    ) {
        DeleteMemoRes result = memoApiFacade.deleteMemo(auth.memberDto().id(), req);
        return ApiRes.onSuccess(result);
    }

}
