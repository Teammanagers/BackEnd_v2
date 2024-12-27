package kr.teammangers.dev.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.teammangers.dev.global.common.enums.EntityStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentDto(
        Long id,
        String content,
        Boolean hidden,

        Long memberId,

        // BaseField
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime createdAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime updatedAt,
        EntityStatus useYn
) {
}
