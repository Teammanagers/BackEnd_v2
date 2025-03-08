package kr.teammangers.dev.todo.application;

import kr.teammangers.dev.global.error.code.ErrorStatus;
import kr.teammangers.dev.global.error.exception.GeneralException;
import kr.teammangers.dev.tag.domain.repository.team_member.TeamMemberTagRepository;
import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.team.domain.entity.TeamMember;
import kr.teammangers.dev.team.domain.repository.TeamMemberRepository;
import kr.teammangers.dev.todo.domain.Todo;
import kr.teammangers.dev.todo.dto.TodoListDto;
import kr.teammangers.dev.todo.dto.TodoDto;
import kr.teammangers.dev.todo.dto.req.CreateTodoReq;
import kr.teammangers.dev.todo.dto.req.UpdateTodoReq;
import kr.teammangers.dev.todo.dto.res.*;
import kr.teammangers.dev.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kr.teammangers.dev.tag.mapper.TagMapper.TAG_MAPPER;
import static kr.teammangers.dev.todo.mapper.TodoMapper.TODO_MAPPER;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoCrudService {

    private final TodoRepository todoRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamMemberTagRepository teamMemberTagRepository;

    @Transactional
    public TodoCommonRes createTodo(Long teamMemberId, CreateTodoReq request) {
        TeamMember teamMember = teamMemberRepository.findById(teamMemberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TEAMMEMBER_NOT_FOUND));

        Todo newTodo = TODO_MAPPER.toEntity(request, teamMember);
        todoRepository.save(newTodo);

        return TODO_MAPPER.toCommonRes(newTodo);

    }

    @Transactional
    public TodoCommonRes updateTodoContent(Long todoId, UpdateTodoReq request) {
        Todo todoForUpdate = todoRepository.findById(todoId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TODO_NOT_FOUND));

        todoForUpdate.updateTitle(request.title());

        return TODO_MAPPER.toCommonRes(todoForUpdate);

    }

    @Transactional
    public void deleteTodo(Long memberId, Long todoId) {
        Todo todoForDelete = todoRepository.findById(todoId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TODO_NOT_FOUND));

        if (!teamMemberRepository.existsByTeam_IdAndMember_Id(memberId, todoForDelete.getTeamMember().getTeam().getId())) {
            throw new GeneralException(ErrorStatus.TODO_FORBIDDEN);
        }

        todoRepository.deleteById(todoId);
    }

    public GetTeamTodoRes getTeamTodo(Long memberId, Long teamId) {
        if (!teamMemberRepository.existsByTeam_IdAndMember_Id(memberId, teamId)) {
            throw new GeneralException(ErrorStatus.TEAM_FORBIDDEN);
        }

        AtomicInteger pending = new AtomicInteger();
        AtomicInteger in_progress = new AtomicInteger();
        AtomicInteger completed = new AtomicInteger();

        List<TodoListDto> teamTodoList = teamMemberRepository.findAllByTeam_Id(teamId)
                .stream().map(teamMember -> {
                    String name = teamMember.getMember().getName();
                    Long teamMemberId = teamMember.getId();
                    List<TagDto> tagList = teamMemberTagRepository.findAllByTeamMember_Id(teamMemberId)
                            .stream()
                            .map(teamMemberTag -> TAG_MAPPER.toDto(teamMemberTag.getTag())).toList();
                    List<TodoDto> todoList = todoRepository.findAllByTeamMember_Id(teamMemberId)
                            .stream().map(
                                    todo -> {
                                        switch(todo.getStatus()) {
                                            case PENDING -> pending.getAndIncrement();
                                            case IN_PROGRESS -> in_progress.getAndIncrement();
                                            case COMPLETED -> completed.getAndIncrement();
                                        }
                                    return TODO_MAPPER.toDto(todo);
                                    }).toList();
                    return TODO_MAPPER.toTodoListDto(teamMember.getId(), name, tagList, todoList);
                }).toList();

        return TODO_MAPPER.toGetTeamTodoRes(teamTodoList, pending.get(), in_progress.get(), completed.get());
    }

    @Transactional
    public TodoCommonRes updateTodoStatus(Long memberId, Long todoId, Integer option) {
        Todo todoForUpdate = todoRepository.findById(todoId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TODO_NOT_FOUND));

        todoForUpdate.updateStatus(option);

        return TODO_MAPPER.toCommonRes(todoForUpdate);
    }

}
