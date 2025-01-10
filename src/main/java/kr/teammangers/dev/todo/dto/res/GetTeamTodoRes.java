package kr.teammangers.dev.todo.dto.res;

import kr.teammangers.dev.todo.dto.MemberTodoListDto;
import lombok.Builder;

import java.util.List;

@Builder
public record GetTeamTodoRes(
        List<MemberTodoListDto> teamTodoList,
        Integer pending,
        Integer in_progress,
        Integer completed
) {
}
