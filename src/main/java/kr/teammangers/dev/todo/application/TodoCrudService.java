package kr.teammangers.dev.todo.application;

import kr.teammangers.dev.common.payload.code.dto.enums.ErrorStatus;
import kr.teammangers.dev.common.payload.exception.GeneralException;
import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.team.domain.mapping.TeamManage;
import kr.teammangers.dev.team.repository.mapping.TeamManageRepository;
import kr.teammangers.dev.todo.domain.Todo;
import kr.teammangers.dev.todo.dto.MemberTodoListDto;
import kr.teammangers.dev.todo.dto.TodoDto;
import kr.teammangers.dev.todo.dto.req.CreateTodoReq;
import kr.teammangers.dev.todo.dto.req.UpdateTodoReq;
import kr.teammangers.dev.todo.dto.res.CreateTodoRes;
import kr.teammangers.dev.todo.dto.res.GetTeamTodoRes;
import kr.teammangers.dev.todo.dto.res.UpdateTodoRes;
import kr.teammangers.dev.todo.dto.res.UpdateTodoStatusRes;
import kr.teammangers.dev.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoCrudService {

    private final TodoRepository todoRepository;
    private final TeamManageRepository teamManageRepository;

    public CreateTodoRes createTodo(Long teamManageId, CreateTodoReq request) {
        TeamManage teamManage = teamManageRepository.findById(teamManageId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TEAMMANAGE_NOT_FOUND));

        Todo newTodo = request.toTodo();
        newTodo.setTeamManage(teamManage);

        long newTodoId = todoRepository.save(newTodo).getId();

        return CreateTodoRes.builder()
                .createdTodoId(newTodoId)
                .build();
    }

    public UpdateTodoRes updateTodoContent(Long todoId, UpdateTodoReq request) {
        Todo todoForUpdate = todoRepository.findById(todoId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TODO_NOT_FOUND));

        todoForUpdate.updateTitle(request.title());

        return UpdateTodoRes.builder()
                .updatedTodoId(todoId)
                .build();
    }

    public void deleteTodo(Long memberId, Long todoId) {
        Todo todoForDelete = todoRepository.findById(todoId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TODO_NOT_FOUND));

        if (!teamManageRepository.existsByTeam_IdAndMember_Id(memberId, todoForDelete.getTeamManage().getTeam().getId())) {
            throw new GeneralException(ErrorStatus.TODO_FORBIDDEN);
        }

        todoRepository.deleteById(todoId);
    }

    public GetTeamTodoRes getTeamTodo(Long memberId, Long teamId) {
        if (!teamManageRepository.existsByTeam_IdAndMember_Id(memberId, teamId)) {
            throw new GeneralException(ErrorStatus.TEAM_FORBIDDEN);
        }

        List<MemberTodoListDto> teamTodoList = teamManageRepository.findAllByTeam_Id(teamId)
                .stream().map(teamManage -> {
                    String name = teamManage.getMember().getName();
                    // TODO: TagList 추가 필요
                    List<TodoDto> todoList = todoRepository.findAllByTeamManage_Id(teamManage.getId())
                            .stream().map(TodoDto::from).toList();
                    return MemberTodoListDto.builder()
                            .teamManageId(teamManage.getId())
                            .name(name)
                            .todoList(todoList)
                            .build();
                }).toList();

        return GetTeamTodoRes.builder()
                .teamTodoList(teamTodoList)
                .build();
    }

    public UpdateTodoStatusRes updateTodoStatus(Long memberId, Long todoId, Integer option) {
        Todo todoForUpdate = todoRepository.findById(todoId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TODO_NOT_FOUND));

        todoForUpdate.updateStatus(option);

        return UpdateTodoStatusRes.builder()
                .updatedTodoId(todoId)
                .build();
    }

}
