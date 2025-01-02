package kr.teammangers.dev.notice.application.facade;

import kr.teammangers.dev.notice.application.service.NoticeService;
import kr.teammangers.dev.notice.dto.NoticeDto;
import kr.teammangers.dev.notice.dto.request.CreateNoticeReq;
import kr.teammangers.dev.notice.dto.request.UpdateNoticeReq;
import kr.teammangers.dev.notice.dto.response.GetNoticeRes;
import kr.teammangers.dev.team.application.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.teammangers.dev.notice.mapper.NoticeResMapper.NOTICE_RES_MAPPER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeApiFacade {

    private final NoticeService noticeService;
    private final TeamService teamService;

    @Transactional
    public NoticeDto createNotice(Long memberId, Long teamId, CreateNoticeReq req) {
        teamService.validateTeamAdmin(teamId, memberId);
        return noticeService.save(teamId, req.content());
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
    public NoticeDto updateNotice(Long memberId, Long teamId, Long noticeId, UpdateNoticeReq req) {
        teamService.validateTeamAdmin(teamId, memberId);
        return noticeService.update(noticeId, req.content());
    }

    @Transactional
    public Long deleteNotice(Long memberId, Long teamId, Long noticeId) {
        teamService.validateTeamAdmin(teamId, memberId);
        return noticeService.delete(noticeId);
    }

}
