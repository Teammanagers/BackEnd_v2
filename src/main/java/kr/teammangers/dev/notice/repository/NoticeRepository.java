package kr.teammangers.dev.notice.repository;

import kr.teammangers.dev.notice.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
