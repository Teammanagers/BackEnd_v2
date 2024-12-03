package kr.teammangers.dev.todo.dto.req;

import kr.teammangers.dev.common.enums.TodoStatus;
import kr.teammangers.dev.todo.domain.Todo;
import lombok.Builder;

@Builder
public record CreateTodoReq(
        String title
) {
    public Todo toTodo() {
        return Todo.builder()
                .title(title)
                .status(TodoStatus.PENDING)
                .build();
    }
}
