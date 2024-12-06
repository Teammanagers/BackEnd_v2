package kr.teammangers.dev.memo.repository;

import kr.teammangers.dev.memo.domain.Memo;

import java.util.List;

public interface MemoQueryDsl {
    List<Memo> findAllByOptions(Long folderId, Boolean isFixed);
}
