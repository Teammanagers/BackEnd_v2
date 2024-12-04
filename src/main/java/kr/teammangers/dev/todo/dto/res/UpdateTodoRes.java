package kr.teammangers.dev.todo.dto.res;

import lombok.Builder;

@Builder
public record UpdateTodoRes(
        Long todoId
) {
}
