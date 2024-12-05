package kr.teammangers.dev.memo.repository;

import kr.teammangers.dev.memo.domain.Memo;

import java.util.List;

public interface MemoQueryDslRepository {
    List<Memo> findAllByOptions(Long folderId, Boolean isFixed);
}
