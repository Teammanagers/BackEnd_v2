package kr.teammangers.dev.memo.application.impl;

import kr.teammangers.dev.common.payload.exception.GeneralException;
import kr.teammangers.dev.memo.application.FolderService;
import kr.teammangers.dev.memo.domain.Folder;
import kr.teammangers.dev.memo.dto.FolderDto;
import kr.teammangers.dev.memo.dto.req.UpdateFolderReq;
import kr.teammangers.dev.memo.repository.FolderRepository;
import kr.teammangers.dev.memo.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.teammangers.dev.common.payload.code.dto.enums.ErrorStatus.FOLDER_NOT_FOUND;
import static kr.teammangers.dev.memo.mapper.FolderMapper.FOLDER_MAPPER;

@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {

    private final FolderRepository folderRepository;
    private final MemoRepository memoRepository;

    @Override
    public FolderDto save(FolderDto folderDto) {
        Folder parent = folderDto.parentId() != null ? folderRepository.getReferenceById(folderDto.parentId()) : null;
        Folder folder = FOLDER_MAPPER.toEntity(folderDto, parent);
        return FOLDER_MAPPER.toDto(folderRepository.save(folder));
    }

    @Override
    public List<FolderDto> findAllDtoByParentId(Long parentId) {
        return findAllByParentId(parentId).stream()
                .map(FOLDER_MAPPER::toDto)
                .toList();
    }

    @Override
    public FolderDto findDtoById(Long folderId) {
        return folderRepository.findById(folderId)
                .map(FOLDER_MAPPER::toDto)
                .orElseThrow(() -> new GeneralException(FOLDER_NOT_FOUND));
    }


    @Override
    public FolderDto update(UpdateFolderReq req) {
        Folder folder = findById(req.folderId());
        folder.update(req);
        return FOLDER_MAPPER.toDto(folder);
    }


    @Override
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
        return folderRepository.findById(id).orElse(null);
    }

}
