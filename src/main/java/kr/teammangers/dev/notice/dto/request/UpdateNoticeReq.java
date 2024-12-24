package kr.teammangers.dev.notice.dto.request;

public record UpdateNoticeReq(
        Long noticeId,
        String content
) {
}
