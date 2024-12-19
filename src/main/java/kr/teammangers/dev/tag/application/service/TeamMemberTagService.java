package kr.teammangers.dev.tag.application.service;

import kr.teammangers.dev.tag.domain.entity.TeamMemberTag;
import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.tag.mapper.TagMapper;
import kr.teammangers.dev.tag.domain.repository.team_member.TeamMemberTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamMemberTagService {

    private final TeamMemberTagRepository teamMemberTagRepository;

    public List<TagDto> findAllTagDtoByTeamMemberId(Long teamMemberId) {
        return teamMemberTagRepository.findAllByTeamMember_Id(teamMemberId).stream()
                .map(TeamMemberTag::getTag)
                .map(TagMapper.TAG_MAPPER::toDto)
                .toList();
    }
}
