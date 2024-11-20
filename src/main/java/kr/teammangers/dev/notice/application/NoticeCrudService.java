package kr.teammangers.dev.notice.application;

import kr.teammangers.dev.notice.dto.req.CreateNoticeReq;
import kr.teammangers.dev.notice.dto.res.GetNoticeRes;

public interface NoticeCrudService {
    void createNotice(Long memberId, Long teamId, CreateNoticeReq req);

    GetNoticeRes getNotice(Long teamId);
}
