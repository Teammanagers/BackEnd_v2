package kr.teammangers.dev.todo.mapper;

import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.team.domain.entity.TeamMember;
import kr.teammangers.dev.todo.domain.Todo;
import kr.teammangers.dev.todo.dto.TodoListDto;
import kr.teammangers.dev.todo.dto.TodoDto;
import kr.teammangers.dev.todo.dto.req.CreateTodoReq;
import kr.teammangers.dev.todo.dto.res.GetTeamTodoRes;
import kr.teammangers.dev.todo.dto.res.TodoCommonRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TodoMapper {

    TodoMapper TODO_MAPPER = Mappers.getMapper(TodoMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "title", source = "createTodoReq.title")
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "teamMember", source = "teamMember")
    Todo toEntity(CreateTodoReq createTodoReq, TeamMember teamMember);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "status", source = "status")
    TodoDto toDto(Todo todo);

    @Mapping(target = "id", source = "id")
    TodoCommonRes toCommonRes(Todo todo);

    TodoListDto toTodoListDto(Long teamMemberId, String name, List<TagDto> tagList, List<TodoDto> todoList);

    GetTeamTodoRes toGetTeamTodoRes(List<TodoListDto> teamTodoList, Integer pending, Integer in_progress, Integer completed);

}
