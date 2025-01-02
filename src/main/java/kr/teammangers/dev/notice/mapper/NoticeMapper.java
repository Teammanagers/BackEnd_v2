package kr.teammangers.dev.notice.mapper;

import kr.teammangers.dev.notice.domain.entity.Notice;
import kr.teammangers.dev.notice.dto.NoticeDto;
import kr.teammangers.dev.team.domain.entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NoticeMapper {

    NoticeMapper NOTICE_MAPPER = Mappers.getMapper(NoticeMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "content", source = "content")
    @Mapping(target = "team", source = "team")
    Notice toEntity(String content, Team team);

    @Mapping(target = "teamId", source = "team.id")
    NoticeDto toDto(Notice notice);

}
