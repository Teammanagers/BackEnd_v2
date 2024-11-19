package kr.teammangers.dev.s3.application;

public interface TeamImgService {
    Long save(Long teamId, Long s3FileInfoId);

    String findFilePathByTeamId(Long teamId);
}
