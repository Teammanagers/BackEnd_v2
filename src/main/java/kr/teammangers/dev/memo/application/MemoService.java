package kr.teammangers.dev.memo.application;

import kr.teammangers.dev.memo.dto.MemoDto;
import kr.teammangers.dev.memo.dto.req.CreateMemoReq;
import kr.teammangers.dev.memo.dto.req.UpdateMemoReq;

import java.util.List;

public interface MemoService {
    MemoDto save(CreateMemoReq req);

    List<MemoDto> findAllDtoByFolderId(Long folderId);

    MemoDto update(UpdateMemoReq req);

    void deleteById(Long memoId);

    void validateMemoAdmin(Long memoId, Long memberId);

    Boolean updateFixStatus(Long memoId);
}
