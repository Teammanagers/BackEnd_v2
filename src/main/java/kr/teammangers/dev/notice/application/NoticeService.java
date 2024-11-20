package kr.teammangers.dev.notice.application;

import kr.teammangers.dev.notice.dto.NoticeDto;

import java.util.List;

public interface NoticeService {
    NoticeDto save(Long teamId, String content);

    NoticeDto findRecentDtoByTeamId(Long teamId);

    List<NoticeDto> findAllDtoByTeamId(Long teamId);
}
