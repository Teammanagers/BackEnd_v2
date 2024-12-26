package kr.teammangers.dev.member.mapper;

import kr.teammangers.dev.member.domain.entity.Comment;
import kr.teammangers.dev.member.domain.entity.Member;
import kr.teammangers.dev.member.dto.CommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentMapper COMMENT_MAPPER = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "content", source = "content")
    @Mapping(target = "hidden", expression = "java(false)")
    @Mapping(target = "member", source = "member")
    Comment toEntity(Member member, String content);

    @Mapping(target = "memberId", source =" comment.member.id")
    CommentDto toDto(Comment comment);
}
