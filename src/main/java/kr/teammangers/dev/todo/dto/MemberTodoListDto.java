package kr.teammangers.dev.todo.dto;

import kr.teammangers.dev.tag.dto.TagDto;
import lombok.Builder;

import java.util.List;

@Builder
public record MemberTodoListDto(
        Long teamManageId,
        String name,
        List<TagDto> tagList,
        List<TodoDto> todoList
) {
}
