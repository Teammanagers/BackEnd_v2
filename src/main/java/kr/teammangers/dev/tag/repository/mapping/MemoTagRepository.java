package kr.teammangers.dev.tag.repository.mapping;

import kr.teammangers.dev.tag.domain.mapping.MemoTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoTagRepository extends JpaRepository<MemoTag, Long> {
}
