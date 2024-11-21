package kr.teammangers.dev.memo.presentation;

import kr.teammangers.dev.auth.dto.AuthInfo;
import kr.teammangers.dev.common.payload.ApiRes;
import kr.teammangers.dev.memo.application.MemoCrudService;
import kr.teammangers.dev.memo.dto.req.CreateMemoReq;
import kr.teammangers.dev.memo.dto.res.CreateMemoRes;
import kr.teammangers.dev.memo.dto.res.GetMemoRes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/team/{teamId}/memo")
public class MemoController {

    private final MemoCrudService memoCrudService;

    @PostMapping
    public ApiRes<CreateMemoRes> createMemo(
            @AuthenticationPrincipal final AuthInfo auth,
            @PathVariable("teamId") final Long teamId,
            @RequestBody final CreateMemoReq req
    ) {
        CreateMemoRes result = memoCrudService.createMemo(auth.memberDto().id(), teamId, req);
        return ApiRes.onSuccess(result);
    }

    @GetMapping("/list")
    public ApiRes<List<GetMemoRes>> getMemoListByTeam(
            @PathVariable("teamId") final Long teamId
    ) {
        return null;
    }

}
