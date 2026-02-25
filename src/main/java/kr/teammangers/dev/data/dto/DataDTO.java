package kr.teammangers.dev.data.dto;

import kr.teammangers.dev.s3.dto.S3FileInfoDto;
import kr.teammangers.dev.tag.dto.TagDto;

public record DataDTO(
        Long dataId,
        Long teamMemberId,
        String name,
//        TagDto tagDto,
        S3FileInfoDto fileInfo,
        String fileUrl

) {
    public static DataDTO of(Long dataId, Long teamMemberId, String name, S3FileInfoDto fileInfo, String generatedUrl) {
        return new DataDTO(dataId, teamMemberId, name, fileInfo, generatedUrl);
    }
}
