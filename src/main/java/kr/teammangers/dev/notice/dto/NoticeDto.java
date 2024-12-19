package kr.teammangers.dev.notice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.teammangers.dev.global.common.enums.EntityStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record NoticeDto(
        Long id,
        String content,

        Long teamId,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime createdAt,
        Long createdBy,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime updatedAt,
        Long updatedBy,
        EntityStatus useYn
) {
}
