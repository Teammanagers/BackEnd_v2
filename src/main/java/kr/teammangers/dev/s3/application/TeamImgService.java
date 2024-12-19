package kr.teammangers.dev.s3.application;

import kr.teammangers.dev.global.common.payload.code.dto.enums.ErrorStatus;
import kr.teammangers.dev.global.common.payload.exception.GeneralException;
import kr.teammangers.dev.s3.domain.S3FileInfo;
import kr.teammangers.dev.s3.domain.mapping.TeamImg;
import kr.teammangers.dev.s3.repository.S3Repository;
import kr.teammangers.dev.s3.repository.mapping.TeamImgRepository;
import kr.teammangers.dev.team.domain.Team;
import kr.teammangers.dev.team.repository.TeamRepository;
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
