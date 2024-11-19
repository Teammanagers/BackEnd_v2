package kr.teammangers.dev.s3.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.teammangers.dev.common.enums.EntityStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record S3FileInfoDto(
        Long id,
        String originalFileName,
        String physicalFileName,
        String filePath,
        String fileNameExtension,
        Long fileSize,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime createdAt,
        Long createdBy,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime updatedAt,
        Long updatedBy,
        EntityStatus useYn
) {
}
