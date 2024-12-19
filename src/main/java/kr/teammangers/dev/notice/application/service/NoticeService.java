package kr.teammangers.dev.notice.application.service;

import kr.teammangers.dev.global.error.exception.GeneralException;
import kr.teammangers.dev.notice.domain.entity.Notice;
import kr.teammangers.dev.notice.dto.NoticeDto;
import kr.teammangers.dev.notice.domain.repository.NoticeRepository;
import kr.teammangers.dev.team.domain.Team;
import kr.teammangers.dev.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.teammangers.dev.global.error.code.ErrorStatus.NOTICE_NOT_FOUND;
import static kr.teammangers.dev.notice.mapper.NoticeMapper.NOTICE_MAPPER;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final TeamRepository teamRepository;

    public NoticeDto save(Long teamId, String content) {
        return NOTICE_MAPPER.toDto(insert(teamId, content));
    }

    public NoticeDto update(Long noticeId, String content) {
        Notice notice = findById(noticeId);
        notice.updateContent(content);
        return NOTICE_MAPPER.toDto(notice);
    }

    public Long delete(Long noticeId) {
        noticeRepository.deleteById(noticeId);
        return noticeId;
    }

    public NoticeDto findRecentDtoByTeamId(Long teamId) {
        return NOTICE_MAPPER.toDto(findRecentByTeamId(teamId));
    }

    public List<NoticeDto> findAllDtoByTeamId(Long teamId) {
        return findAllByTeamId(teamId).stream()
                .map(NOTICE_MAPPER::toDto)
                .toList();
    }

    private Notice insert(Long teamId, String content) {
        Team team = teamRepository.getReferenceById(teamId);
        return noticeRepository.save(NOTICE_MAPPER.toEntity(content, team));
    }

    private Notice findById(Long id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> new GeneralException(NOTICE_NOT_FOUND));
    }

    private Notice findRecentByTeamId(Long teamId) {
        return noticeRepository.findTopRecentByTeamId(teamId)
                .orElseThrow(() -> new GeneralException(NOTICE_NOT_FOUND));
    }

    private List<Notice> findAllByTeamId(Long teamId) {
        return noticeRepository.findAllRecentByTeamId(teamId);
    }

}
