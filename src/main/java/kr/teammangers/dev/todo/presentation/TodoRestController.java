package kr.teammangers.dev.todo.presentation;

import kr.teammangers.dev.auth.dto.AuthInfo;
import kr.teammangers.dev.common.payload.ApiRes;
import kr.teammangers.dev.todo.application.TodoCrudService;
import kr.teammangers.dev.todo.dto.req.CreateTodoReq;
import kr.teammangers.dev.todo.dto.req.UpdateTodoReq;
import kr.teammangers.dev.todo.dto.res.CreateTodoRes;
import kr.teammangers.dev.todo.dto.res.GetTeamTodoRes;
import kr.teammangers.dev.todo.dto.res.UpdateTodoRes;
import kr.teammangers.dev.todo.dto.res.UpdateTodoStatusRes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/todo")
public class TodoRestController {

    private final TodoCrudService todoCrudService;

    @PostMapping
    public ApiRes<CreateTodoRes> createTodo(
            @AuthenticationPrincipal final AuthInfo auth,
            @RequestParam(name = "teamManageId") final Long teamManageId,
            @RequestBody final CreateTodoReq request
            ) {

        return ApiRes.onSuccess(todoCrudService.createTodo(teamManageId, request));

    }

    @PostMapping("/{todoId}")
    public ApiRes<UpdateTodoRes> updateTodo(
            @AuthenticationPrincipal final AuthInfo auth,
            @PathVariable(name = "todoId") final Long todoId,
            @RequestBody final UpdateTodoReq request
            ) {

        return ApiRes.onSuccess(todoCrudService.updateTodoContent(todoId, request));

    }

    @DeleteMapping("/{todoId}")
    public ApiRes<Void> deleteTodo(
            @AuthenticationPrincipal final AuthInfo auth,
            @PathVariable(name = "todoId") final Long todoId
    ) {

        todoCrudService.deleteTodo(auth.memberDto().id(), todoId);
        return ApiRes.onSuccess();

    }

    @GetMapping
    public ApiRes<GetTeamTodoRes> getTeamTodo(
            @AuthenticationPrincipal final AuthInfo auth,
            @RequestParam(name = "teamId") final Long teamId
    ) {

        return ApiRes.onSuccess(todoCrudService.getTeamTodo(auth.memberDto().id(), teamId));

    }

    @PatchMapping("/{todoId}")
    public ApiRes<UpdateTodoStatusRes> updateTodoStatus(
            @AuthenticationPrincipal final AuthInfo auth,
            @PathVariable(name = "todoId") final Long todoId,
            @RequestParam(name = "option") final Integer option
    ) {

        return ApiRes.onSuccess(todoCrudService.updateTodoStatus(auth.memberDto().id(), todoId, option));

    }
}
