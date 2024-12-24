package kr.teammangers.dev.notice.presentation;

import kr.teammangers.dev.auth.infrastructure.security.AuthInfo;
import kr.teammangers.dev.global.common.response.ApiRes;
import kr.teammangers.dev.notice.application.facade.NoticeApiFacade;
import kr.teammangers.dev.notice.dto.request.CreateNoticeReq;
import kr.teammangers.dev.notice.dto.request.DeleteNoticeReq;
import kr.teammangers.dev.notice.dto.request.UpdateNoticeReq;
import kr.teammangers.dev.notice.dto.response.CreateNoticeRes;
import kr.teammangers.dev.notice.dto.response.DeleteNoticeRes;
import kr.teammangers.dev.notice.dto.response.GetNoticeRes;
import kr.teammangers.dev.notice.dto.response.UpdateNoticeRes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/team/{teamId}/notice")
public class NoticeController {

    private final NoticeApiFacade noticeApiFacade;

    @PostMapping
    public ApiRes<CreateNoticeRes> createNotice(
            @AuthenticationPrincipal final AuthInfo auth,
            @PathVariable("teamId") final Long teamId,
            @RequestBody final CreateNoticeReq req
    ) {
        CreateNoticeRes result = noticeApiFacade.createNotice(auth.memberDto().id(), teamId, req);
        return ApiRes.onSuccess(result);
    }

    @GetMapping
    public ApiRes<GetNoticeRes> getNotice(
            @PathVariable("teamId") final Long teamId
    ) {
        GetNoticeRes result = noticeApiFacade.getNotice(teamId);
        return ApiRes.onSuccess(result);
    }

    @GetMapping("/list")
    public ApiRes<List<GetNoticeRes>> getNoticeList(
            @PathVariable("teamId") final Long teamId
    ) {
        List<GetNoticeRes> result = noticeApiFacade.getNoticeList(teamId);
        return ApiRes.onSuccess(result);
    }

    @PatchMapping
    public ApiRes<UpdateNoticeRes> updateNotice(
            @AuthenticationPrincipal final AuthInfo auth,
            @PathVariable("teamId") final Long teamId,
            @RequestBody final UpdateNoticeReq req
    ) {
        UpdateNoticeRes result = noticeApiFacade.updateNotice(auth.memberDto().id(), teamId, req);
        return ApiRes.onSuccess(result);
    }

    @DeleteMapping
    public ApiRes<DeleteNoticeRes> deleteNotice(
            @AuthenticationPrincipal final AuthInfo auth,
            @PathVariable("teamId") final Long teamId,
            @RequestBody final DeleteNoticeReq req
    ) {
        DeleteNoticeRes result = noticeApiFacade.deleteNotice(auth.memberDto().id(), teamId, req);
        return ApiRes.onSuccess(result);
    }

}
