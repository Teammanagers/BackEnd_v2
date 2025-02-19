package kr.teammangers.dev.document.application.service;

import kr.teammangers.dev.document.domain.entity.Document;
import kr.teammangers.dev.document.domain.repository.DocumentRepository;
import kr.teammangers.dev.document.dto.DocumentDto;
import kr.teammangers.dev.global.error.code.ErrorStatus;
import kr.teammangers.dev.global.error.exception.GeneralException;
import kr.teammangers.dev.s3.domain.entity.S3FileInfo;
import kr.teammangers.dev.s3.domain.repository.S3Repository;
import kr.teammangers.dev.team.domain.entity.TeamMember;
import kr.teammangers.dev.team.domain.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.teammangers.dev.document.mapper.DocumentMapper.DOCUMENT_MAPPER;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final S3Repository s3Repository;

    public Long save(Long teamId, Long memberId, Long s3InfoId) {
        TeamMember teamMember = teamMemberRepository.findByTeam_IdAndMember_Id(teamId, memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TEAM_MEMBER_NOT_FOUND));
        S3FileInfo s3FileInfo = s3Repository.getReferenceById(s3InfoId);

        Document newDocument = DOCUMENT_MAPPER.toEntity(teamMember, s3FileInfo);

        return documentRepository.save(newDocument).getId();
    }

    public List<DocumentDto> findAllDocumentByTeamMemberId(Long teamMemberId) {
        return documentRepository.findAllByTeamMemberId(teamMemberId).stream()
                .map(DOCUMENT_MAPPER::toDto)
                .toList();
    }
}
