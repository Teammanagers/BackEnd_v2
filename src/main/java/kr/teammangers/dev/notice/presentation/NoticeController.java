package kr.teammangers.dev.notice.presentation;

import kr.teammangers.dev.auth.infrastructure.security.AuthInfo;
import kr.teammangers.dev.global.common.response.ApiRes;
import kr.teammangers.dev.notice.application.facade.NoticeApiFacade;
import kr.teammangers.dev.notice.dto.NoticeDto;
import kr.teammangers.dev.notice.dto.request.CreateNoticeReq;
import kr.teammangers.dev.notice.dto.request.UpdateNoticeReq;
import kr.teammangers.dev.notice.dto.response.GetNoticeRes;
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
    public ApiRes<NoticeDto> createNotice(
            @AuthenticationPrincipal final AuthInfo auth,
            @PathVariable("teamId") final Long teamId,
            @RequestBody final CreateNoticeReq req
    ) {
        NoticeDto result = noticeApiFacade.createNotice(auth.memberDto().id(), teamId, req);
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

    @PatchMapping("/{noticeId}")
    public ApiRes<NoticeDto> updateNotice(
            @AuthenticationPrincipal final AuthInfo auth,
            @PathVariable("teamId") final Long teamId,
            @PathVariable("noticeId") final Long noticeId,
            @RequestBody final UpdateNoticeReq req
    ) {
        NoticeDto result = noticeApiFacade.updateNotice(auth.memberDto().id(), teamId, noticeId, req);
        return ApiRes.onSuccess(result);
    }

    @DeleteMapping("/{noticeId}")
    public ApiRes<Long> deleteNotice(
            @AuthenticationPrincipal final AuthInfo auth,
            @PathVariable("teamId") final Long teamId,
            @PathVariable("noticeId") final Long noticeId
    ) {
        Long result = noticeApiFacade.deleteNotice(auth.memberDto().id(), teamId, noticeId);
        return ApiRes.onSuccess(result);
    }

}
