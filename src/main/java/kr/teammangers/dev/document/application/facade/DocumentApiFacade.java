package kr.teammangers.dev.document.application.facade;

import kr.teammangers.dev.document.application.service.DocumentService;
import kr.teammangers.dev.document.dto.DocumentDto;
import kr.teammangers.dev.document.dto.response.GetTeamDocumentRes;
import kr.teammangers.dev.s3.application.service.S3Service;
import kr.teammangers.dev.s3.dto.S3FileInfoDto;
import kr.teammangers.dev.tag.application.service.TagService;
import kr.teammangers.dev.tag.application.service.TeamMemberTagService;
import kr.teammangers.dev.tag.application.service.TeamTagService;
import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.tag.dto.TeamMemberTagDto;
import kr.teammangers.dev.team.application.service.TeamMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static kr.teammangers.dev.document.mapper.DocumentResMapper.DOCUMENT_RES_MAPPER;

import static kr.teammangers.dev.s3.constant.S3Constant.DOCUMENT_PATH;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DocumentApiFacade {

    private final DocumentService documentService;
    private final TeamMemberService teamMemberService;
    private final TeamMemberTagService teamMemberTagService;
    private final S3Service s3Service;

    @Transactional
    public Long postDocument(Long teamId, Long memberId, MultipartFile file) {

        S3FileInfoDto s3FileInfoDto = s3Service.uploadFile(file, DOCUMENT_PATH);

        return documentService.save(teamId, memberId, s3FileInfoDto.id());
    }

    public GetTeamDocumentRes getTeamDocument(Long teamId) {

        List<Long> teamMemberIdList = teamMemberService.findAllTeamMemberIdByTeamId(teamId);

        List<DocumentDto> documentDtoList = teamMemberIdList.stream()
                .flatMap(teamMemberId -> documentService.findAllDocumentByTeamMemberId(teamMemberId).stream()).toList();
        List<List<TagDto>> teamMemberTagDtoList = teamMemberIdList.stream()
                .map(teamMemberTagService::findAllTagDtoByTeamMemberId).toList();

        return DOCUMENT_RES_MAPPER.toGetTeamDocumentRes(documentDtoList, teamMemberTagDtoList);
    }

}
