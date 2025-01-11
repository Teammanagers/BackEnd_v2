package kr.teammangers.dev.todo.dto;

import kr.teammangers.dev.tag.dto.TagDto;

import java.util.List;

public record TodoListDto(
        Long teamMemberId,
        String name,
        List<TagDto> tagList,
        List<TodoDto> todoList
) {
}
