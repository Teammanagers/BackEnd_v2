package kr.teammangers.dev.todo.application.facade;

import kr.teammangers.dev.s3.application.service.S3Service;
import kr.teammangers.dev.s3.application.service.TodoImgService;
import kr.teammangers.dev.s3.dto.S3FileInfoDto;
import kr.teammangers.dev.todo.dto.res.TodoCommonRes;
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
    private final S3Service s3Service;

    public TodoCommonRes uploadImage(Long todoId, MultipartFile imageFile) {

        S3FileInfoDto s3FileInfoDto = s3Service.uploadFile(imageFile, TODO_IMAGE_PATH);

        return TODO_MAPPER.toCommonRes(todoImgService.save(todoId, s3FileInfoDto.id()));
    }

}
