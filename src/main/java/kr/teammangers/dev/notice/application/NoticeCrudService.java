package kr.teammangers.dev.notice.application;

import kr.teammangers.dev.notice.dto.NoticeDto;
import kr.teammangers.dev.notice.dto.req.CreateNoticeReq;
import kr.teammangers.dev.notice.dto.req.DeleteNoticeReq;
import kr.teammangers.dev.notice.dto.req.UpdateNoticeReq;
import kr.teammangers.dev.notice.dto.res.CreateNoticeRes;
import kr.teammangers.dev.notice.dto.res.DeleteNoticeRes;
import kr.teammangers.dev.notice.dto.res.GetNoticeRes;
import kr.teammangers.dev.notice.dto.res.UpdateNoticeRes;
import kr.teammangers.dev.team.application.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.teammangers.dev.notice.mapper.NoticeResMapper.NOTICE_RES_MAPPER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeCrudService {

    private final NoticeService noticeService;
    private final TeamService teamService;

    @Transactional
    public CreateNoticeRes createNotice(Long memberId, Long teamId, CreateNoticeReq req) {
        teamService.validateTeamAdmin(teamId, memberId);
        return NOTICE_RES_MAPPER.toCreate(noticeService.save(teamId, req.content()));
    }

    public GetNoticeRes getNotice(Long teamId) {
        NoticeDto noticeDto = noticeService.findRecentDtoByTeamId(teamId);
        return NOTICE_RES_MAPPER.toGet(noticeDto);
    }

    public List<GetNoticeRes> getNoticeList(Long teamId) {
        return noticeService.findAllDtoByTeamId(teamId).stream()
                .map(NOTICE_RES_MAPPER::toGet)
                .toList();
    }

    @Transactional
    public UpdateNoticeRes updateNotice(Long memberId, Long teamId, UpdateNoticeReq req) {
        teamService.validateTeamAdmin(teamId, memberId);
        return NOTICE_RES_MAPPER.toUpdate(noticeService.update(req.noticeId(), req.content()));
    }

    @Transactional
    public DeleteNoticeRes deleteNotice(Long memberId, Long teamId, DeleteNoticeReq req) {
        teamService.validateTeamAdmin(teamId, memberId);
        return NOTICE_RES_MAPPER.toDelete(noticeService.delete(req.noticeId()));
    }

}
