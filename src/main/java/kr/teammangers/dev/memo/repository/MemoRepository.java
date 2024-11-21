package kr.teammangers.dev.memo.repository;

import kr.teammangers.dev.memo.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {

    List<Memo> findAllByTeam_Id(Long teamId);

}
