package kr.teammangers.dev.s3.domain.repository;

import kr.teammangers.dev.s3.domain.entity.TodoImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoImgRepository extends JpaRepository<TodoImg, Long> {

}
