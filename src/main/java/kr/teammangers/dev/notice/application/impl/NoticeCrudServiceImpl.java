package kr.teammangers.dev.notice.application.impl;

import kr.teammangers.dev.common.payload.exception.GeneralException;
import kr.teammangers.dev.notice.application.NoticeCrudService;
import kr.teammangers.dev.notice.application.NoticeService;
import kr.teammangers.dev.notice.dto.NoticeDto;
import kr.teammangers.dev.notice.dto.req.CreateNoticeReq;
import kr.teammangers.dev.notice.dto.res.GetNoticeRes;
import kr.teammangers.dev.team.application.base.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static kr.teammangers.dev.common.payload.code.dto.enums.ErrorStatus.NOTICE_NO_AUTHORITY;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeCrudServiceImpl implements NoticeCrudService {

    private final NoticeService noticeService;
    private final TeamService teamService;

    @Override
    @Transactional
    public void createNotice(Long memberId, Long teamId, CreateNoticeReq req) {
        if (!Objects.equals(teamService.findDtoById(teamId).createdBy(), memberId)) {
            throw new GeneralException(NOTICE_NO_AUTHORITY);
        }
        noticeService.save(teamId, req.content());
    }

    @Override
    public GetNoticeRes getNotice(Long teamId) {
        NoticeDto noticeDto = noticeService.findRecentDtoByTeamId(teamId);
        return GetNoticeRes.builder().notice(noticeDto).build();
    }

}
