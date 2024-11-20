package kr.teammangers.dev.notice.application.impl;

import kr.teammangers.dev.notice.application.NoticeService;
import kr.teammangers.dev.notice.domain.Notice;
import kr.teammangers.dev.notice.dto.NoticeDto;
import kr.teammangers.dev.notice.repository.NoticeRepository;
import kr.teammangers.dev.team.domain.Team;
import kr.teammangers.dev.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    private Notice insert(Long teamId, String content) {
        Team team = teamRepository.getReferenceById(teamId);
        return noticeRepository.save(NOTICE_MAPPER.toEntity(content, team));
    }

}
