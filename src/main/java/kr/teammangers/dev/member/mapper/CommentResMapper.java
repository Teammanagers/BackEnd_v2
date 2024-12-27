package kr.teammangers.dev.member.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentResMapper {

    CommentResMapper COMMENT_RES_MAPPER = Mappers.getMapper(CommentResMapper.class);

}
