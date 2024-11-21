package kr.teammangers.dev.memo.repository;

import kr.teammangers.dev.memo.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> {


}
