package kr.teammangers.dev.todo.presentation;

import kr.teammangers.dev.auth.dto.AuthInfo;
import kr.teammangers.dev.common.payload.ApiRes;
import kr.teammangers.dev.todo.application.TodoCrudService;
import kr.teammangers.dev.todo.dto.req.CreateTodoReq;
import kr.teammangers.dev.todo.dto.res.CreateTodoRes;
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
}
