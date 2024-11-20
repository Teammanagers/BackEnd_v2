package kr.teammangers.dev.notice.application.impl;

import kr.teammangers.dev.common.payload.exception.GeneralException;
import kr.teammangers.dev.notice.application.NoticeService;
import kr.teammangers.dev.notice.domain.Notice;
import kr.teammangers.dev.notice.dto.NoticeDto;
import kr.teammangers.dev.notice.repository.NoticeRepository;
import kr.teammangers.dev.team.domain.Team;
import kr.teammangers.dev.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.teammangers.dev.common.payload.code.dto.enums.ErrorStatus.*;
import static kr.teammangers.dev.notice.mapper.NoticeMapper.NOTICE_MAPPER;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final TeamRepository teamRepository;

    @Override
    public NoticeDto save(Long teamId, String content) {
        return NOTICE_MAPPER.toDto(insert(teamId, content));
    }

    @Override
    public NoticeDto findRecentDtoByTeamId(Long teamId) {
        return NOTICE_MAPPER.toDto(findRecentByTeamId(teamId));
    }

    @Override
    public List<NoticeDto> findAllDtoByTeamId(Long teamId) {
        return findAllByTeamId(teamId).stream()
                .map(NOTICE_MAPPER::toDto)
                .toList();
    }

    private Notice insert(Long teamId, String content) {
        Team team = teamRepository.getReferenceById(teamId);
        return noticeRepository.save(NOTICE_MAPPER.toEntity(content, team));
    }

    private Notice findRecentByTeamId(Long teamId) {
        return noticeRepository.findTopRecentByTeamId(teamId)
                .orElseThrow(() -> new GeneralException(NOTICE_NOT_FOUND));
    }

    private List<Notice> findAllByTeamId(Long teamId) {
        return noticeRepository.findAllRecentByTeamId(teamId);
    }

}
