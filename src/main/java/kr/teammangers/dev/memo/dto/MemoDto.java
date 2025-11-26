package kr.teammangers.dev.memo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.teammangers.dev.global.common.enums.EntityStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MemoDto(
        Long id,
        String title,
        String content,
        Boolean isFixed,

        Long folderId,
        Long teamId,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime createdAt,
        Long createdBy,
        String createdByName,  // 작성자 이름 추가
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime updatedAt,
        Long updatedBy,
        String updatedByName,  // 수정자 이름 추가
        EntityStatus useYn
) {
}
