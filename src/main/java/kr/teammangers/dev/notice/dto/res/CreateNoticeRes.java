package kr.teammangers.dev.notice.dto.res;

import lombok.Builder;

@Builder
public record CreateNoticeRes(
        Long createdNoticeId
) {
}
