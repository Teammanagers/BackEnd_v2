package kr.teammangers.dev.tag.application;

import kr.teammangers.dev.tag.domain.Tag;
import kr.teammangers.dev.tag.domain.mapping.TeamTag;
import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.tag.repository.TagRepository;
import kr.teammangers.dev.tag.repository.mapping.TeamTagRepository;
import kr.teammangers.dev.team.domain.Team;
import kr.teammangers.dev.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.teammangers.dev.tag.mapper.TagMapper.TAG_MAPPER;
import static kr.teammangers.dev.tag.mapper.TeamTagMapper.TEAM_TAG_MAPPER;

@Service
@RequiredArgsConstructor
public class TeamTagService {

    private final TeamTagRepository teamTagRepository;
    private final TeamRepository teamRepository;
    private final TagRepository tagRepository;

    public Long save(Long teamId, Long tagId) {
        Team team = teamRepository.getReferenceById(teamId);
        Tag tag = tagRepository.getReferenceById(tagId);
        return teamTagRepository.save(TEAM_TAG_MAPPER.toEntity(team, tag)).getId();
    }

    public List<TagDto> findAllTagDtoByTeamId(Long teamId) {
        return findAllByTeamId(teamId).stream()
                .map(teamTag -> TAG_MAPPER.toDto(teamTag.getTag()))
                .toList();
    }

    private List<TeamTag> findAllByTeamId(Long teamId) {
        return teamTagRepository.findAllByTeam_Id(teamId);
    }

    public void deleteAllByOptions(Long teamId, String tagName) {
        teamTagRepository.deleteAllByOptions(teamId, tagName);
    }
}
