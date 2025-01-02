package kr.teammangers.dev.todo.repository;

import kr.teammangers.dev.todo.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findAllByTeamMember_Id(Long teamManageId);
}
