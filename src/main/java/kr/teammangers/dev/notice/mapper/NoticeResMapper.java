package kr.teammangers.dev.notice.mapper;

import kr.teammangers.dev.notice.dto.NoticeDto;
import kr.teammangers.dev.notice.dto.response.CreateNoticeRes;
import kr.teammangers.dev.notice.dto.response.DeleteNoticeRes;
import kr.teammangers.dev.notice.dto.response.GetNoticeRes;
import kr.teammangers.dev.notice.dto.response.UpdateNoticeRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NoticeResMapper {

    NoticeResMapper NOTICE_RES_MAPPER = Mappers.getMapper(NoticeResMapper.class);

    @Mapping(target = "createdNoticeId", source = "id")
    CreateNoticeRes toCreate(NoticeDto noticeDto);

    @Mapping(target = "notice", source = "noticeDto")
    GetNoticeRes toGet(NoticeDto noticeDto);

    @Mapping(target = "updatedNoticeId", source = "id")
    UpdateNoticeRes toUpdate(NoticeDto noticeDto);

    @Mapping(target = "deletedNoticeId", source = "id")
    DeleteNoticeRes toDelete(Long id);

}
