package kr.teammangers.dev.feedback.domain.repository;

import kr.teammangers.dev.feedback.domain.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findByDataIdOrderByCreatedAtAsc(Long dataId);

    @Modifying
    @Query("UPDATE Feedback f SET f.useYn = 'N' WHERE f.data.id = :dataId")
    void softDeleteAllByDataId(Long dataId);
}
