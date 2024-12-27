package kr.teammangers.dev.memo.application.service;

import kr.teammangers.dev.global.error.exception.GeneralException;
import kr.teammangers.dev.memo.domain.entity.Folder;
import kr.teammangers.dev.memo.domain.entity.Memo;
import kr.teammangers.dev.memo.domain.repository.FolderRepository;
import kr.teammangers.dev.memo.domain.repository.MemoRepository;
import kr.teammangers.dev.memo.dto.MemoDto;
import kr.teammangers.dev.memo.dto.request.CreateMemoReq;
import kr.teammangers.dev.memo.dto.request.UpdateMemoReq;
import kr.teammangers.dev.team.domain.entity.Team;
import kr.teammangers.dev.team.domain.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static kr.teammangers.dev.global.error.code.ErrorStatus.MEMO_NOT_FOUND;
import static kr.teammangers.dev.global.error.code.ErrorStatus.MEMO_NO_AUTHORITY;
import static kr.teammangers.dev.memo.mapper.MemoMapper.MEMO_MAPPER;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final FolderRepository folderRepository;
    private final TeamRepository teamRepository;

    public MemoDto save(Long folderId, Long teamId, CreateMemoReq req) {
        return MEMO_MAPPER.toDto(insert(folderId, teamId, req));
    }

    public List<MemoDto> findAllDtoByFolderId(Long folderId, Boolean isFixed) {
        return memoRepository.findAllByOptions(folderId, isFixed).stream()
                .map(MEMO_MAPPER::toDto)
                .toList();
    }

    public List<MemoDto> findAllDtoByFixed(Long teamId) {
        return memoRepository.findAllByMemoListByFixed(teamId).stream()
                .map(MEMO_MAPPER::toDto)
                .toList();
    }

    public MemoDto update(Long memoId, UpdateMemoReq req) {
        Memo memo = findById(memoId);
        memo.update(req);
        return MEMO_MAPPER.toDto(memo);
    }

    public void deleteById(Long memoId) {
        memoRepository.deleteById(memoId);
    }

    public void validateMemoAdmin(Long memoId, Long memberId) {
        if (!Objects.equals(findById(memoId).getCreatedBy(), memberId)) {
            throw new GeneralException(MEMO_NO_AUTHORITY);
        }
    }

    public MemoDto updateFixStatus(Long memoId) {
        Memo memo = findById(memoId);
        memo.updateFixStatus();
        return MEMO_MAPPER.toDto(memo);
    }

    private Memo insert(Long folderId, Long teamId, CreateMemoReq req) {
        Folder folder = folderRepository.getReferenceById(folderId);
        Team team = teamRepository.getReferenceById(teamId);
        return memoRepository.save(MEMO_MAPPER.toEntity(req, folder, team));
    }

    private Memo findById(Long id) {
        return memoRepository.findById(id)
                .orElseThrow(() -> new GeneralException(MEMO_NOT_FOUND));
    }

}
