package kr.teammangers.dev.todo.dto;

import kr.teammangers.dev.global.common.enums.TodoStatus;

public record TodoDto(
        Long id,
        String title,
        TodoStatus status
) {
}
