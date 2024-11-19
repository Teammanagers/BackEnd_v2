package kr.teammangers.dev.team.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.teammangers.dev.common.enums.EntityStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TeamDto(
        Long id,
        String title,
        String password,
        String code,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime createdAt,
        Long createdBy,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime updatedAt,
        Long updatedBy,
        EntityStatus useYn
) {
}
