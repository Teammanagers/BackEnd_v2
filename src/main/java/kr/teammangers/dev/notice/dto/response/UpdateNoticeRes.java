package kr.teammangers.dev.notice.dto.response;

import lombok.Builder;

@Builder
public record UpdateNoticeRes(
        Long updatedNoticeId
) {
}
