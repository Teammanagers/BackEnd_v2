package kr.teammangers.dev.notice.repository;

import kr.teammangers.dev.notice.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query("SELECT n FROM Notice n WHERE n.team.id = :teamId ORDER BY n.updatedAt DESC LIMIT 1")
    Optional<Notice> findTopRecentByTeamId(@Param("teamId") Long teamId);

    @Query("SELECT n FROM Notice n WHERE n.team.id = :teamId ORDER BY n.updatedAt DESC")
    List<Notice> findAllRecentByTeamId(@Param("teamId") Long teamId);
}
