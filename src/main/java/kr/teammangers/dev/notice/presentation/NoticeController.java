package kr.teammangers.dev.notice.presentation;

import kr.teammangers.dev.auth.dto.AuthInfo;
import kr.teammangers.dev.common.payload.ApiRes;
import kr.teammangers.dev.notice.application.NoticeCrudService;
import kr.teammangers.dev.notice.dto.req.CreateNoticeReq;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/team/{teamId}/notice")
public class NoticeController {

    private final NoticeCrudService noticeCrudService;

    @PostMapping
    public ApiRes<Void> createNotice(
            @AuthenticationPrincipal final AuthInfo auth,
            @PathVariable("teamId") final Long teamId,
            @RequestBody final CreateNoticeReq req
    ) {
        noticeCrudService.createNotice(auth.memberDto().id(), teamId, req);
        return ApiRes.onSuccess();
    }

}
