package kr.teammangers.dev.member.domain.repository;

import kr.teammangers.dev.member.domain.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.member.id = :memberId ORDER BY c.createdAt DESC")
    List<Comment> findAllByRecent(@Param("memberId") Long memberId, Pageable pageable);

}
