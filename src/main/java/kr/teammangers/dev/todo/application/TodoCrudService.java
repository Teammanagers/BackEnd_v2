package kr.teammangers.dev.todo.application;

import kr.teammangers.dev.global.error.code.ErrorStatus;
import kr.teammangers.dev.global.error.exception.GeneralException;
import kr.teammangers.dev.tag.domain.repository.team_member.TeamMemberTagRepository;
import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.tag.mapper.TagMapper;
import kr.teammangers.dev.team.domain.entity.TeamMember;
import kr.teammangers.dev.team.domain.repository.TeamMemberRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoCrudService {

    private final TodoRepository todoRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamMemberTagRepository teamMemberTagRepository;
    private final TagMapper tagMapper;

    @Transactional
    public CreateTodoRes createTodo(Long teamMemberId, CreateTodoReq request) {
        TeamMember teamMember = teamMemberRepository.findById(teamMemberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TEAMMEMBER_NOT_FOUND));

        Todo newTodo = request.toTodo();
        newTodo.setTeamMember(teamMember);

        long newTodoId = todoRepository.save(newTodo).getId();

        return CreateTodoRes.builder()
                .createdTodoId(newTodoId)
                .build();
    }

    @Transactional
    public UpdateTodoRes updateTodoContent(Long todoId, UpdateTodoReq request) {
        Todo todoForUpdate = todoRepository.findById(todoId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TODO_NOT_FOUND));

        todoForUpdate.updateTitle(request.title());

        return UpdateTodoRes.builder()
                .updatedTodoId(todoId)
                .build();
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

        List<MemberTodoListDto> teamTodoList = teamMemberRepository.findAllByTeam_Id(teamId)
                .stream().map(teamMember -> {
                    String name = teamMember.getMember().getName();
                    Long teamMemberId = teamMember.getId();
                    List<TagDto> tagList = teamMemberTagRepository.findAllByTeamMember_Id(teamMemberId)
                            .stream()
                            .map(teamMemberTag -> tagMapper.toDto(teamMemberTag.getTag())).toList();
                    List<TodoDto> todoList = todoRepository.findAllByTeamMember_Id(teamMemberId)
                            .stream().map(TodoDto::from).toList();
                    return MemberTodoListDto.builder()
                            .teamMemberId(teamMember.getId())
                            .tagList(tagList)
                            .name(name)
                            .todoList(todoList)
                            .build();
                }).toList();

        return GetTeamTodoRes.builder()
                .teamTodoList(teamTodoList)
                .build();
    }

    @Transactional
    public UpdateTodoStatusRes updateTodoStatus(Long memberId, Long todoId, Integer option) {
        Todo todoForUpdate = todoRepository.findById(todoId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TODO_NOT_FOUND));

        todoForUpdate.updateStatus(option);

        return UpdateTodoStatusRes.builder()
                .updatedTodoId(todoId)
                .build();
    }

}
