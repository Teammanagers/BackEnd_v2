package kr.teammangers.dev.todo.application.facade;

import kr.teammangers.dev.alarm.application.AlarmService;
import kr.teammangers.dev.alarm.dto.AlarmDto;
import kr.teammangers.dev.global.error.code.ErrorStatus;
import kr.teammangers.dev.global.error.exception.GeneralException;
import kr.teammangers.dev.s3.application.S3Service;
import kr.teammangers.dev.s3.application.TodoImgService;
import kr.teammangers.dev.s3.dto.S3FileInfoDto;
import kr.teammangers.dev.todo.domain.Todo;
import kr.teammangers.dev.todo.dto.res.TodoCommonRes;
import kr.teammangers.dev.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static kr.teammangers.dev.s3.constant.S3Constant.TODO_IMAGE_PATH;
import static kr.teammangers.dev.todo.mapper.TodoMapper.TODO_MAPPER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TodoApiFacade {

    private final TodoImgService todoImgService;
    private final AlarmService alarmService;
    private final S3Service s3Service;

    private final TodoRepository todoRepository;

    @Transactional
    public TodoCommonRes uploadImage(Long todoId, MultipartFile imageFile) {

        S3FileInfoDto s3FileInfoDto = s3Service.uploadFile(imageFile, TODO_IMAGE_PATH);

        return TODO_MAPPER.toCommonRes(todoImgService.save(todoId, s3FileInfoDto.id()));
    }

    @Transactional
    public AlarmDto awake(Long todoId) {

        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TODO_NOT_FOUND));

        Long memberId = todo.getTeamMember().getMember().getId();
        return alarmService.createTodoAwakeAlarm(todoId, memberId);
    }
}
