package kr.teammangers.dev.notice.dto.response;

import kr.teammangers.dev.notice.dto.NoticeDto;
import lombok.Builder;

@Builder
public record GetNoticeRes(
        NoticeDto notice
) {
}
