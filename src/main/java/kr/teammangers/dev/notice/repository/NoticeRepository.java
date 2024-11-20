package kr.teammangers.dev.notice.repository;

import kr.teammangers.dev.notice.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Optional<Notice> findTopByTeamIdOrderByUpdatedAtDesc(Long teamId);

}
