package kr.teammangers.dev.tag.repository.mapping;

import kr.teammangers.dev.tag.domain.mapping.MemoTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemoTagRepository extends JpaRepository<MemoTag, Long> {

    @Query("SELECT mt FROM MemoTag mt WHERE mt.memo.id = :memoId")
    List<MemoTag> findAllByMemoId(@Param("memoId") Long memoId);

    void deleteByMemoIdAndTagName(Long memoId, String tagName);

    void deleteAllByMemoId(Long memoId);

}
