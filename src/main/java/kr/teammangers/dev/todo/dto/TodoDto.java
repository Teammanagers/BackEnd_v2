package kr.teammangers.dev.todo.dto;

import kr.teammangers.dev.todo.domain.Todo;
import lombok.Builder;

@Builder
public record TodoDto(
        Long todoId,
        String title
) {
    public static TodoDto from(Todo todo) {
        return TodoDto.builder()
                .todoId(todo.getId())
                .title(todo.getTitle())
                .build();
    }
}
