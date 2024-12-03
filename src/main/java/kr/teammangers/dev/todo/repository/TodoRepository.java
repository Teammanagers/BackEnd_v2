package kr.teammangers.dev.todo.repository;

import kr.teammangers.dev.todo.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
