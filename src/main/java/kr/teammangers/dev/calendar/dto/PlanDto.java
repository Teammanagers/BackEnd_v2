package kr.teammangers.dev.calendar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.teammangers.dev.global.common.enums.EntityStatus;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record PlanDto(
        Long id,
        LocalDate date,
        String title,
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
