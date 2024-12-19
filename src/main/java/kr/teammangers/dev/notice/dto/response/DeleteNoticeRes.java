package kr.teammangers.dev.notice.dto.response;

import lombok.Builder;

@Builder
public record DeleteNoticeRes(
        Long deletedNoticeId
) {
}
