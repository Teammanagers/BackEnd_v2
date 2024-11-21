package kr.teammangers.dev.memo.presentation;

import kr.teammangers.dev.auth.dto.AuthInfo;
import kr.teammangers.dev.common.payload.ApiRes;
import kr.teammangers.dev.memo.application.MemoCrudService;
import kr.teammangers.dev.memo.dto.req.CreateMemoReq;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/team/{teamId}/memo")
public class MemoController {

    private final MemoCrudService memoCrudService;

    @PostMapping
    public ApiRes<Void> createMemo(
            @AuthenticationPrincipal final AuthInfo auth,
            @PathVariable("teamId") final Long teamId,
            @RequestBody final CreateMemoReq req
    ) {
        memoCrudService.createMemo(auth.memberDto().id(), teamId, req);
        return ApiRes.onSuccess();
    }

}
