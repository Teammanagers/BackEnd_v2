package kr.teammangers.dev.memo.domain.repository;

import kr.teammangers.dev.memo.domain.entity.Memo;

import java.util.List;

public interface MemoRepositoryCustom {
    List<Memo> findAllByOptions(Long folderId, Boolean isFixed);
}
