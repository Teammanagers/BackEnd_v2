package kr.teammangers.dev.inquiry.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.teammangers.dev.global.common.enums.EntityStatus;
import kr.teammangers.dev.inquiry.enums.InquiryType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record InquiryDto(
        Long id,
        InquiryType inquiryType,
        String content,

        Long memberId,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime createdAt,
        Long createdBy,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime updatedAt,
        Long updatedBy,
        EntityStatus useYn
) {
}
