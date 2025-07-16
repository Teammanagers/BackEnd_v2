package kr.teammangers.dev.data.dto;

import kr.teammangers.dev.s3.dto.S3FileInfoDto;

public record DataDTO(
        Long dataId,
        Long teamMemberId,
        S3FileInfoDto fileInfo,
        String fileUrl

) {
    public static DataDTO of(Long dataId, Long teamMemberId, S3FileInfoDto fileInfo, String generatedUrl) {
        return new DataDTO(dataId, teamMemberId, fileInfo, generatedUrl);
    }
}
