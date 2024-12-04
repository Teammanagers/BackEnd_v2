package kr.teammangers.dev.todo.application;

import kr.teammangers.dev.common.payload.code.dto.enums.ErrorStatus;
import kr.teammangers.dev.common.payload.exception.GeneralException;
import kr.teammangers.dev.team.domain.mapping.TeamManage;
import kr.teammangers.dev.team.repository.mapping.TeamManageRepository;
import kr.teammangers.dev.todo.domain.Todo;
import kr.teammangers.dev.todo.dto.req.CreateTodoReq;
import kr.teammangers.dev.todo.dto.req.UpdateTodoReq;
import kr.teammangers.dev.todo.dto.res.CreateTodoRes;
import kr.teammangers.dev.todo.dto.res.UpdateTodoRes;
import kr.teammangers.dev.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .todoId(newTodoId)
                .build();
    }

    public UpdateTodoRes updateTodoContent(Long todoId, UpdateTodoReq request) {
        Todo todoForUpdate = todoRepository.findById(todoId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TODO_NOT_FOUND));

        todoForUpdate.updateTitle(request.title());

        return UpdateTodoRes.builder()
                .todoId(todoId)
                .build();

    }


}
