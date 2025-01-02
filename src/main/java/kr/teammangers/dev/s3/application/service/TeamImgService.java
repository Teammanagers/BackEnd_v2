package kr.teammangers.dev.s3.application.service;

import kr.teammangers.dev.global.error.code.ErrorStatus;
import kr.teammangers.dev.global.error.exception.GeneralException;
import kr.teammangers.dev.s3.domain.entity.S3FileInfo;
import kr.teammangers.dev.s3.domain.entity.TeamImg;
import kr.teammangers.dev.s3.domain.repository.S3Repository;
import kr.teammangers.dev.s3.domain.repository.TeamImgRepository;
import kr.teammangers.dev.team.domain.entity.Team;
import kr.teammangers.dev.team.domain.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kr.teammangers.dev.s3.mapper.TeamImgMapper.TEAM_IMG_MAPPER;

@Service
@RequiredArgsConstructor
public class TeamImgService {

    private final TeamImgRepository teamImgRepository;
    private final TeamRepository teamRepository;
    private final S3Repository s3Repository;

    public Long save(Long teamId, Long s3FileInfoId) {
        Team team = teamRepository.getReferenceById(teamId);
        S3FileInfo s3FileInfo = s3Repository.getReferenceById(s3FileInfoId);
        return teamImgRepository.save(TEAM_IMG_MAPPER.toEntity(team, s3FileInfo)).getId();
    }

    public String findFilePathByTeamId(Long teamId) {
        return teamImgRepository.findByTeam_Id(teamId)
                .map(TeamImg::getS3FileInfo)
                .map(S3FileInfo::getFilePath)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TEAM_IMG_NOT_FOUND));
    }

    public void delete(Long teamId) {
        teamImgRepository.deleteByTeam_Id(teamId);
    }
}
