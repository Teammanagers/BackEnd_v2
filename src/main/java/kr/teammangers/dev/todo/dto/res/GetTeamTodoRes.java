package kr.teammangers.dev.todo.dto.res;

import kr.teammangers.dev.todo.dto.TodoListDto;

import java.util.List;

public record GetTeamTodoRes(
        List<TodoListDto> teamTodoList,
        Integer pending,
        Integer in_progress,
        Integer completed
) {
}
