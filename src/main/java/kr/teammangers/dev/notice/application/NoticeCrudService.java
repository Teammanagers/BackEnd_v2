package kr.teammangers.dev.notice.application;

import kr.teammangers.dev.notice.dto.req.CreateNoticeReq;
import kr.teammangers.dev.notice.dto.req.DeleteNoticeReq;
import kr.teammangers.dev.notice.dto.req.UpdateNoticeReq;
import kr.teammangers.dev.notice.dto.res.CreateNoticeRes;
import kr.teammangers.dev.notice.dto.res.DeleteNoticeRes;
import kr.teammangers.dev.notice.dto.res.GetNoticeRes;
import kr.teammangers.dev.notice.dto.res.UpdateNoticeRes;

import java.util.List;

public interface NoticeCrudService {
    CreateNoticeRes createNotice(Long memberId, Long teamId, CreateNoticeReq req);

    GetNoticeRes getNotice(Long teamId);

    List<GetNoticeRes> getNoticeList(Long teamId);

    UpdateNoticeRes updateNotice(Long memberId, Long teamId, UpdateNoticeReq req);

    DeleteNoticeRes deleteNotice(Long memberId, Long teamId, DeleteNoticeReq req);
}
