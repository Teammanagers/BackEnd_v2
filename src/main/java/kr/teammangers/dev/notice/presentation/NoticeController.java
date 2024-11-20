package kr.teammangers.dev.notice.presentation;

import kr.teammangers.dev.auth.dto.AuthInfo;
import kr.teammangers.dev.common.payload.ApiRes;
import kr.teammangers.dev.notice.application.NoticeCrudService;
import kr.teammangers.dev.notice.dto.req.CreateNoticeReq;
import kr.teammangers.dev.notice.dto.req.UpdateNoticeReq;
import kr.teammangers.dev.notice.dto.res.GetNoticeRes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ApiRes<GetNoticeRes> getNotice(
            @PathVariable("teamId") final Long teamId
    ) {
        GetNoticeRes result = noticeCrudService.getNotice(teamId);
        return ApiRes.onSuccess(result);
    }

    @GetMapping("/list")
    public ApiRes<List<GetNoticeRes>> getNoticeList(
            @PathVariable("teamId") final Long teamId
    ) {
        List<GetNoticeRes> result = noticeCrudService.getNoticeList(teamId);
        return ApiRes.onSuccess(result);
    }

    @PatchMapping
    public ApiRes<Void> updateNotice(
            @AuthenticationPrincipal final AuthInfo auth,
            @PathVariable("teamId") final Long teamId,
            @RequestBody final UpdateNoticeReq req
    ) {
        noticeCrudService.updateNotice(auth.memberDto().id(), teamId, req);
        return ApiRes.onSuccess();
    }

}
