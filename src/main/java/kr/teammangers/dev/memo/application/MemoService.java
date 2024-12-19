package kr.teammangers.dev.memo.application;

import kr.teammangers.dev.global.error.exception.GeneralException;
import kr.teammangers.dev.memo.domain.Folder;
import kr.teammangers.dev.memo.dto.MemoDto;
import kr.teammangers.dev.memo.dto.req.CreateMemoReq;
import kr.teammangers.dev.memo.dto.req.UpdateMemoReq;
import kr.teammangers.dev.memo.repository.FolderRepository;
import kr.teammangers.dev.memo.repository.MemoRepository;
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

    public MemoDto save(CreateMemoReq req) {
        Folder folder = folderRepository.getReferenceById(req.folderId());
        return MEMO_MAPPER.toDto(insert(req, folder));
    }

    public List<MemoDto> findAllDtoByFolderId(Long folderId, Boolean isFixed) {
        return memoRepository.findAllByOptions(folderId, isFixed).stream()
                .map(MEMO_MAPPER::toDto)
                .toList();
    }

    public MemoDto update(UpdateMemoReq req) {
        kr.teammangers.dev.memo.domain.Memo memo = findById(req.memoId());
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

    public Boolean updateFixStatus(Long memoId) {
        kr.teammangers.dev.memo.domain.Memo memo = findById(memoId);
        memo.updateFixStatus();
        return memo.getIsFixed();
    }

    private kr.teammangers.dev.memo.domain.Memo insert(CreateMemoReq req, Folder folder) {
        return memoRepository.save(MEMO_MAPPER.toEntity(req, folder));
    }

    private kr.teammangers.dev.memo.domain.Memo findById(Long id) {
        return memoRepository.findById(id)
                .orElseThrow(() -> new GeneralException(MEMO_NOT_FOUND));
    }

}
