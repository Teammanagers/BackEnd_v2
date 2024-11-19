package kr.teammangers.dev.s3.application.impl;

import kr.teammangers.dev.common.payload.code.dto.enums.ErrorStatus;
import kr.teammangers.dev.common.payload.exception.GeneralException;
import kr.teammangers.dev.s3.domain.S3FileInfo;
import kr.teammangers.dev.s3.repository.S3Repository;
import kr.teammangers.dev.s3.application.TeamImgService;
import kr.teammangers.dev.team.domain.Team;
import kr.teammangers.dev.team.domain.mapping.TeamImg;
import kr.teammangers.dev.team.repository.TeamRepository;
import kr.teammangers.dev.s3.repository.mapping.TeamImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kr.teammangers.dev.team.mapper.TeamImgMapper.TEAM_IMG_MAPPER;

@Service
@RequiredArgsConstructor
public class TeamImgServiceImpl implements TeamImgService {

    private final TeamImgRepository teamImgRepository;
    private final TeamRepository teamRepository;
    private final S3Repository s3Repository;

    @Override
    public Long save(Long teamId, Long s3FileInfoId) {
        Team team = teamRepository.getReferenceById(teamId);
        S3FileInfo s3FileInfo = s3Repository.getReferenceById(s3FileInfoId);
        return teamImgRepository.save(TEAM_IMG_MAPPER.toEntity(team, s3FileInfo)).getId();
    }

    @Override
    public String findFilePathByTeamId(Long teamId) {
        return teamImgRepository.findByTeam_Id(teamId)
                .map(TeamImg::getS3FileInfo)
                .map(S3FileInfo::getFilePath)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TEAM_IMG_NOT_FOUND));
    }

}
