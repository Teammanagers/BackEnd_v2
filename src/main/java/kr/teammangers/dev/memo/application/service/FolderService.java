package kr.teammangers.dev.memo.application.service;

import kr.teammangers.dev.global.error.exception.GeneralException;
import kr.teammangers.dev.memo.domain.entity.Folder;
import kr.teammangers.dev.memo.domain.repository.FolderRepository;
import kr.teammangers.dev.memo.domain.repository.MemoRepository;
import kr.teammangers.dev.memo.dto.FolderDto;
import kr.teammangers.dev.memo.dto.request.UpdateFolderReq;
import kr.teammangers.dev.team.domain.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.teammangers.dev.global.error.code.ErrorStatus.FOLDER_NOT_FOUND;
import static kr.teammangers.dev.global.error.code.ErrorStatus.TEAM_NOT_FOUND;
import static kr.teammangers.dev.memo.mapper.FolderMapper.FOLDER_MAPPER;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;
    private final MemoRepository memoRepository;
    private final TeamRepository teamRepository;

    public FolderDto save(FolderDto folderDto) {
        Folder parent = folderDto.parentId() != null ? folderRepository.getReferenceById(folderDto.parentId()) : null;
        Folder folder = FOLDER_MAPPER.toEntity(folderDto, parent);
        return FOLDER_MAPPER.toDto(folderRepository.save(folder));
    }

    public List<FolderDto> findAllDtoByParentId(Long parentId) {
        return findAllByParentId(parentId).stream()
                .map(FOLDER_MAPPER::toDto)
                .toList();
    }

    public FolderDto findDtoByTeamId(Long teamId) {
        Folder rootFolder = teamRepository.findById(teamId)
                .orElseThrow(() -> new GeneralException(TEAM_NOT_FOUND))
                .getRootFolder();
        return FOLDER_MAPPER.toDto(rootFolder);
    }

    public FolderDto findDtoById(Long folderId) {
        return folderRepository.findById(folderId)
                .map(FOLDER_MAPPER::toDto)
                .orElseThrow(() -> new GeneralException(FOLDER_NOT_FOUND));
    }

    public FolderDto update(Long folderId, UpdateFolderReq req) {
        Folder folder = findById(folderId);
        folder.update(req);
        return FOLDER_MAPPER.toDto(folder);
    }

    public void deleteAllByFolderId(Long folderId) {
        // 모든 자식 폴더 ID를 가져옴
        List<Long> allFolderIds = folderRepository.findAllDescendantFolderIds(folderId);

        // 관련된 메모 삭제
        memoRepository.deleteAllByFolderIds(allFolderIds);

        // 폴더 삭제
        folderRepository.deleteAllByIds(allFolderIds);
    }

    private List<Folder> findAllByParentId(Long parentId) {
        return folderRepository.findAllByParentId(parentId);
    }

    private Folder findById(Long id) {
        return folderRepository.findById(id)
                .orElseThrow(() -> new GeneralException(FOLDER_NOT_FOUND));
    }

}
