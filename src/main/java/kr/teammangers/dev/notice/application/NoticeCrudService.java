package kr.teammangers.dev.notice.application;

import kr.teammangers.dev.notice.dto.req.CreateNoticeReq;

public interface NoticeCrudService {
    void createNotice(Long memberId, Long teamId, CreateNoticeReq req);
}
