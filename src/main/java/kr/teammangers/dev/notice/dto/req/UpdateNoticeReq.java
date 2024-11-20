package kr.teammangers.dev.notice.dto.req;

public record UpdateNoticeReq(
        Long noticeId,
        String content
) {
}
