package kr.teammangers.dev.data.application;

import kr.teammangers.dev.data.domain.entity.Data;
import kr.teammangers.dev.data.domain.repository.DataRepository;
import kr.teammangers.dev.data.dto.DataDTO;
import kr.teammangers.dev.data.dto.response.CreateDataRes;
import kr.teammangers.dev.data.dto.response.GetDataRes;
import kr.teammangers.dev.global.error.code.ErrorStatus;
import kr.teammangers.dev.global.error.exception.GeneralException;
import kr.teammangers.dev.s3.application.DataFileService;
import kr.teammangers.dev.s3.application.S3Service;
import kr.teammangers.dev.s3.dto.S3FileInfoDto;
import kr.teammangers.dev.tag.application.service.TeamMemberTagService;
import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.team.application.service.TeamMemberService;
import kr.teammangers.dev.team.domain.entity.TeamMember;
import kr.teammangers.dev.team.domain.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import kr.teammangers.dev.feedback.application.FeedbackService;

import java.util.List;

import static kr.teammangers.dev.s3.constant.S3Constant.TEAM_DATA_PATH;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DataCrudService {

    private final S3Service s3Service;
    private final DataFileService dataFileService;
    private final FeedbackService feedbackService;
    private final TeamMemberTagService teamMemberTagService;
    private final TeamMemberService teamMemberService;

    private final DataRepository dataRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Transactional
    public CreateDataRes createData(Long memberId, Long teamId, MultipartFile file) {

        TeamMember teamMember = teamMemberRepository.findByTeam_IdAndMember_Id(teamId, memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TEAM_MEMBER_NOT_FOUND));

        Data newData = new Data(teamMember);
        dataRepository.save(newData);

        S3FileInfoDto s3FileInfoDto = s3Service.uploadFile(file, TEAM_DATA_PATH);
        dataFileService.save(newData.getId(), s3FileInfoDto.id());

        return CreateDataRes.from(newData);
    }

    @Transactional
    public void deleteData(Long dataId) {
        // 1. 관련 피드백 모두 논리적 삭제
        feedbackService.deleteAllFeedbacksByDataId(dataId);

        // 2. S3 파일 정보 및 실제 파일 삭제
        dataFileService.deleteByDataId(dataId);

        // 3. Data 엔티티 논리적 삭제
        dataRepository.deleteById(dataId);
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

                    String name = teamMemberService.findMemberDtoByTeamMemberId(teamMemberId).name();

//                    List<TagDto> tagDtoList = teamMemberTagService.findAllTagDtoByTeamMemberId(teamMemberId);
//                    TagDto tag = null;
//                    if (!tagDtoList.isEmpty()) {
//                        tag = tagDtoList.getFirst();
//                    }

                    String filePath = dataFileService.findFilePathByDataId(dataId);
                    S3FileInfoDto fileInfoDto = dataFileService.getDataFileInfo(dataId);
                    String generatedUrl = s3Service.generateUrl(filePath);

                    return DataDTO.of(dataId, teamMemberId, name, fileInfoDto, generatedUrl);
                })
                .toList();

        return GetDataRes.of(dataDtoList);
    }
}
