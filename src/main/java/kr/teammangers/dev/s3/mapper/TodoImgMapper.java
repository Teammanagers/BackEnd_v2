package kr.teammangers.dev.s3.mapper;

import kr.teammangers.dev.s3.domain.entity.S3FileInfo;
import kr.teammangers.dev.s3.domain.entity.TodoImg;
import kr.teammangers.dev.todo.domain.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TodoImgMapper{

    TodoImgMapper TODO_IMG_MAPPER = Mappers.getMapper(TodoImgMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "todo", source = "todo")
    @Mapping(target = "s3FileInfo", source = "s3FileInfo")
    TodoImg toEntity(Todo todo, S3FileInfo s3FileInfo);
}
