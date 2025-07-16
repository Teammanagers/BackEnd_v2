package kr.teammangers.dev.data.application;

import kr.teammangers.dev.data.domain.entity.Data;
import kr.teammangers.dev.data.domain.repository.DataRepository;
import kr.teammangers.dev.data.dto.DataDTO;
import kr.teammangers.dev.data.dto.response.GetDataRes;
import kr.teammangers.dev.global.error.code.ErrorStatus;
import kr.teammangers.dev.global.error.exception.GeneralException;
import kr.teammangers.dev.s3.application.service.DataFileService;
import kr.teammangers.dev.s3.application.service.S3Service;
import kr.teammangers.dev.s3.dto.S3FileInfoDto;
import kr.teammangers.dev.team.domain.entity.TeamMember;
import kr.teammangers.dev.team.domain.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static kr.teammangers.dev.s3.constant.S3Constant.TEAM_DATA_PATH;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DataCrudService {

    private final S3Service s3Service;
    private final DataFileService dataFileService;

    private final DataRepository dataRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Transactional
    public void createData(Long memberId, Long teamId, MultipartFile file) {

        TeamMember teamMember = teamMemberRepository.findByTeam_IdAndMember_Id(teamId, memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TEAM_MEMBER_NOT_FOUND));

        Data newData = new Data(teamMember);
        dataRepository.save(newData);

        S3FileInfoDto s3FileInfoDto = s3Service.uploadFile(file, TEAM_DATA_PATH);
        dataFileService.save(newData.getId(), s3FileInfoDto.id());
    }

    public GetDataRes getTeamData(Long teamId) {

        List<TeamMember> teamMemberList = teamMemberRepository.findAllByTeam_Id(teamId);
        List<Data> dataList = teamMemberList.stream()
                .flatMap(teamMember -> dataRepository.findAllByTeamMemberId(teamMember.getId()).stream())
                .toList();

        List<DataDTO> dataDtoList =  dataList.stream()
                .map(data -> {
                    Long dataId = data.getId();
                    Long teamMemberId = data.getTeamMember().getId();
                    String filePath = dataFileService.findFilePathByDataId(dataId);
                    S3FileInfoDto fileInfoDto = dataFileService.getDataFileInfo(dataId);
                    String generatedUrl = s3Service.generateUrl(filePath);

                    return DataDTO.of(dataId, teamMemberId, fileInfoDto, generatedUrl);
                })
                .toList();

        return GetDataRes.of(dataDtoList);
    }
}
