package kr.teammangers.dev.tag.application.service;

import kr.teammangers.dev.global.error.exception.GeneralException;
import kr.teammangers.dev.tag.domain.entity.Tag;
import kr.teammangers.dev.tag.domain.entity.TeamTag;
import kr.teammangers.dev.tag.domain.repository.TagRepository;
import kr.teammangers.dev.tag.domain.repository.team.TeamTagRepository;
import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.tag.dto.TeamTagDto;
import kr.teammangers.dev.team.domain.entity.Team;
import kr.teammangers.dev.team.domain.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.teammangers.dev.global.error.code.ErrorStatus.TEAM_TAG_NOT_FOUND;
import static kr.teammangers.dev.tag.mapper.TagMapper.TAG_MAPPER;
import static kr.teammangers.dev.tag.mapper.TeamTagMapper.TEAM_TAG_MAPPER;

@Service
@RequiredArgsConstructor
public class TeamTagService {

    private final TeamTagRepository teamTagRepository;
    private final TeamRepository teamRepository;
    private final TagRepository tagRepository;

    public TeamTagDto save(Long teamId, Long tagId) {
        Team team = teamRepository.getReferenceById(teamId);
        Tag tag = tagRepository.getReferenceById(tagId);
        TeamTag teamTag = teamTagRepository.save(TEAM_TAG_MAPPER.toEntity(team, tag));
        return TEAM_TAG_MAPPER.toDto(teamTag);
    }

    public TeamTagDto update(Long teamId, Long oldTagId, Long newTagId) {
        TeamTag teamTag = findEntityByTagIdAndTeamId(oldTagId, teamId);
        Tag newTag = tagRepository.getReferenceById(newTagId);
        teamTag.updateTag(newTag);
        return TEAM_TAG_MAPPER.toDto(teamTag);
    }

    public Long findIdByTagIdAndTeamId(Long tagId, Long teamId) {
        TeamTag teamTag = findEntityByTagIdAndTeamId(tagId, teamId);
        return teamTag.getId();
    }

    public List<TagDto> findAllTagDtoByTeamId(Long teamId) {
        return findAllByTeamId(teamId).stream()
                .map(teamTag -> TAG_MAPPER.toDto(teamTag.getTag()))
                .toList();
    }

    private TeamTag findEntityByTagIdAndTeamId(Long tagId, Long teamId) {
        return teamTagRepository.findByTag_IdAndTeam_Id(tagId, teamId)
                .orElseThrow(() -> new GeneralException(TEAM_TAG_NOT_FOUND));
    }

    private List<TeamTag> findAllByTeamId(Long teamId) {
        return teamTagRepository.findAllByTeam_Id(teamId);
    }

    public void delete(Long teamTagId) {
        teamTagRepository.deleteById(teamTagId);
    }

}
