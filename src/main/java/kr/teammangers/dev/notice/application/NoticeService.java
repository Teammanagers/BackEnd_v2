package kr.teammangers.dev.notice.application;

import kr.teammangers.dev.notice.dto.NoticeDto;

public interface NoticeService {
    NoticeDto save(Long teamId, String content);

    NoticeDto findRecentDtoByTeamId(Long teamId);
}
