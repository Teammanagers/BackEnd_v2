package kr.teammangers.dev.notice.mapper;

import kr.teammangers.dev.notice.dto.NoticeDto;
import kr.teammangers.dev.notice.dto.response.GetNoticeRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NoticeResMapper {

    NoticeResMapper NOTICE_RES_MAPPER = Mappers.getMapper(NoticeResMapper.class);

    @Mapping(target = "notice", source = "noticeDto")
    GetNoticeRes toGet(NoticeDto noticeDto);

}
