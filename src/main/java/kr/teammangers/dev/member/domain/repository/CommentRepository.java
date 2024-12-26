package kr.teammangers.dev.member.domain.repository;

import kr.teammangers.dev.member.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
