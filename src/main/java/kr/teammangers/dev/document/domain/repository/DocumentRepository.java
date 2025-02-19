package kr.teammangers.dev.document.domain.repository;

import kr.teammangers.dev.document.domain.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findAllByTeamMemberId(Long teamMemberId);
}
