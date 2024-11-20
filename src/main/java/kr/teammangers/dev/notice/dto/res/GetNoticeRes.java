package kr.teammangers.dev.notice.dto.res;

import kr.teammangers.dev.notice.dto.NoticeDto;
import lombok.Builder;

@Builder
public record GetNoticeRes(
        NoticeDto notice
) {
}
