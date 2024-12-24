package kr.teammangers.dev.tag.application.service;

import kr.teammangers.dev.global.error.exception.GeneralException;
import kr.teammangers.dev.tag.domain.entity.Tag;
import kr.teammangers.dev.tag.domain.entity.TeamMemberTag;
import kr.teammangers.dev.tag.domain.repository.TagRepository;
import kr.teammangers.dev.tag.domain.repository.team_member.TeamMemberTagRepository;
import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.tag.mapper.TagMapper;
import kr.teammangers.dev.team.domain.entity.TeamMember;
import kr.teammangers.dev.team.domain.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.teammangers.dev.global.error.code.ErrorStatus.TEAM_MEMBER_NOT_FOUND;
import static kr.teammangers.dev.global.error.code.ErrorStatus.TEAM_MEMBER_TAG_NOT_FOUND;
import static kr.teammangers.dev.tag.mapper.TeamMemberTagMapper.TEAM_MEMBER_TAG_MAPPER;

@Service
@RequiredArgsConstructor
public class TeamMemberTagService {

    private final TeamMemberTagRepository teamMemberTagRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final TagRepository tagRepository;

    public Long save(Long memberId, Long teamId, Long tagId) {
        TeamMember teamMember = teamMemberRepository.findByTeam_IdAndMember_Id(teamId, memberId)
                .orElseThrow(() -> new GeneralException(TEAM_MEMBER_NOT_FOUND));
        Tag tag = tagRepository.getReferenceById(tagId);
        return teamMemberTagRepository.save(TEAM_MEMBER_TAG_MAPPER.toEntity(teamMember, tag)).getId();

    }

    public void delete(Long teamMemberTagId) {
        teamMemberTagRepository.deleteById(teamMemberTagId);
    }

    public List<TagDto> findAllTagDtoByTeamMemberId(Long teamMemberId) {
        return teamMemberTagRepository.findAllByTeamMember_Id(teamMemberId).stream()
                .map(TeamMemberTag::getTag)
                .map(TagMapper.TAG_MAPPER::toDto)
                .toList();
    }

    public Long findIdByTagIdAndTeamIdAndMemberId(Long tagId, Long teamId, Long memberId) {
        TeamMember teamMember = teamMemberRepository.findByTeam_IdAndMember_Id(teamId, memberId)
                .orElseThrow(() -> new GeneralException(TEAM_MEMBER_NOT_FOUND));
        TeamMemberTag teamMemberTag = teamMemberTagRepository.findByTeamMember_IdAndTag_Id(teamMember.getId(), tagId)
                .orElseThrow(() -> new GeneralException(TEAM_MEMBER_TAG_NOT_FOUND));
        return teamMemberTag.getId();
    }
}
