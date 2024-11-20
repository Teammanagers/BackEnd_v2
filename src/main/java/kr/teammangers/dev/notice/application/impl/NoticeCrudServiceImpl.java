package kr.teammangers.dev.notice.application.impl;

import kr.teammangers.dev.notice.application.NoticeCrudService;
import kr.teammangers.dev.notice.application.NoticeService;
import kr.teammangers.dev.notice.dto.NoticeDto;
import kr.teammangers.dev.notice.dto.req.CreateNoticeReq;
import kr.teammangers.dev.notice.dto.req.DeleteNoticeReq;
import kr.teammangers.dev.notice.dto.req.UpdateNoticeReq;
import kr.teammangers.dev.notice.dto.res.GetNoticeRes;
import kr.teammangers.dev.team.application.base.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeCrudServiceImpl implements NoticeCrudService {

    private final NoticeService noticeService;
    private final TeamService teamService;

    @Override
    @Transactional
    public void createNotice(Long memberId, Long teamId, CreateNoticeReq req) {
        teamService.validateTeamAdmin(teamId, memberId);
        noticeService.save(teamId, req.content());
    }

    @Override
    public GetNoticeRes getNotice(Long teamId) {
        NoticeDto noticeDto = noticeService.findRecentDtoByTeamId(teamId);
        return GetNoticeRes.builder().notice(noticeDto).build();
    }

    @Override
    public List<GetNoticeRes> getNoticeList(Long teamId) {
        return noticeService.findAllDtoByTeamId(teamId).stream()
                .map(noticeDto -> GetNoticeRes.builder().notice(noticeDto).build())
                .toList();
    }

    @Override
    @Transactional
    public void updateNotice(Long memberId, Long teamId, UpdateNoticeReq req) {
        teamService.validateTeamAdmin(teamId, memberId);
        noticeService.update(req.noticeId(), req.content());
    }

    @Override
    @Transactional
    public void deleteNotice(Long memberId, Long teamId, DeleteNoticeReq req) {
        teamService.validateTeamAdmin(teamId, memberId);
        noticeService.delete(req.noticeId());
    }

}
