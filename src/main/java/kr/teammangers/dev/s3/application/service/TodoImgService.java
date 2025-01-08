package kr.teammangers.dev.s3.application.service;

import kr.teammangers.dev.s3.domain.entity.S3FileInfo;
import kr.teammangers.dev.s3.domain.repository.S3Repository;
import kr.teammangers.dev.s3.domain.repository.TodoImgRepository;
import kr.teammangers.dev.todo.domain.Todo;
import kr.teammangers.dev.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kr.teammangers.dev.s3.mapper.TodoImgMapper.TODO_IMG_MAPPER;
@Service
@RequiredArgsConstructor
public class TodoImgService {

    private final TodoImgRepository todoImgRepository;
    private final TodoRepository todoRepository;
    private final S3Repository s3Repository;

    public Long save(Long todoId, Long s3FileInfoId) {
        Todo todo = todoRepository.getReferenceById(todoId);
        S3FileInfo s3FileInfo = s3Repository.getReferenceById(s3FileInfoId);
        return todoImgRepository.save(TODO_IMG_MAPPER.toEntity(todo, s3FileInfo)).getId();
    }
}
