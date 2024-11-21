package kr.teammangers.dev.tag.repository.mapping;

import kr.teammangers.dev.tag.domain.mapping.MemoTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoTagRepository extends JpaRepository<MemoTag, Long> {

    List<MemoTag> findAllByMemoId(Long memoId);

    void deleteByMemoIdAndTagName(Long memoId, String tagName);

    void deleteAllByMemoId(Long memoId);

}
