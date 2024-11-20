package kr.teammangers.dev.notice.application;

import kr.teammangers.dev.notice.dto.req.CreateNoticeReq;
import kr.teammangers.dev.notice.dto.req.UpdateNoticeReq;
import kr.teammangers.dev.notice.dto.res.GetNoticeRes;

import java.util.List;

public interface NoticeCrudService {
    void createNotice(Long memberId, Long teamId, CreateNoticeReq req);

    GetNoticeRes getNotice(Long teamId);

    List<GetNoticeRes> getNoticeList(Long teamId);

    void updateNotice(Long memberId, Long teamId, UpdateNoticeReq req);
}
