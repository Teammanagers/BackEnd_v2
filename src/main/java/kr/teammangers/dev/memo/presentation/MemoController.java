package kr.teammangers.dev.memo.presentation;

import kr.teammangers.dev.auth.infrastructure.security.AuthInfo;
import kr.teammangers.dev.global.common.response.ApiRes;
import kr.teammangers.dev.memo.application.facade.MemoApiFacade;
import kr.teammangers.dev.memo.dto.MemoDto;
import kr.teammangers.dev.memo.dto.request.CreateMemoReq;
import kr.teammangers.dev.memo.dto.request.UpdateMemoReq;
import kr.teammangers.dev.memo.dto.response.GetMemoRes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/memo")
public class MemoController {

    private final MemoApiFacade memoApiFacade;

    @PostMapping("/folders/{folderId}/teams/{teamId}")
    public ApiRes<MemoDto> createMemo(
            @PathVariable("folderId") final Long folderId,
            @PathVariable("teamId") final Long teamId,
            @RequestBody final CreateMemoReq req
    ) {
        MemoDto result = memoApiFacade.createMemo(folderId, teamId, req);
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

    @PatchMapping("/{memoId}")
    public ApiRes<MemoDto> updateMemo(
            @AuthenticationPrincipal final AuthInfo auth,
            @PathVariable("memoId") final Long memoId,
            @RequestBody final UpdateMemoReq req
    ) {
        MemoDto result = memoApiFacade.updateMemo(auth.memberDto().id(), memoId, req);
        return ApiRes.onSuccess(result);
    }

    @PatchMapping("/{memoId}/fixing")
    public ApiRes<MemoDto> fixMemo(
            @PathVariable("memoId") final Long memoId
    ) {
        MemoDto result = memoApiFacade.fixMemo(memoId);
        return ApiRes.onSuccess(result);
    }

    @DeleteMapping("/{memoId}")
    public ApiRes<Long> deleteMemo(
            @AuthenticationPrincipal final AuthInfo auth,
            @PathVariable("memoId") final Long memoId
    ) {
        Long result = memoApiFacade.deleteMemo(auth.memberDto().id(), memoId);
        return ApiRes.onSuccess(result);
    }

}
