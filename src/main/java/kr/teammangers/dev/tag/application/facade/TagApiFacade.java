package kr.teammangers.dev.tag.application.facade;

import kr.teammangers.dev.tag.application.service.TagService;
import kr.teammangers.dev.tag.application.service.TeamMemberTagService;
import kr.teammangers.dev.tag.application.service.TeamTagService;
import kr.teammangers.dev.tag.domain.enums.TagType;
import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.tag.dto.TeamMemberTagDto;
import kr.teammangers.dev.tag.dto.TeamTagDto;
import kr.teammangers.dev.tag.dto.request.*;
import kr.teammangers.dev.tag.dto.response.DeleteTagRes;
import kr.teammangers.dev.tag.dto.response.UpdateTagRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagApiFacade {

    private final TagService tagService;
    private final TeamTagService teamTagService;
    private final TeamMemberTagService teamMemberTagService;

    @Transactional
    public TeamTagDto createTeamTag(Long teamId, CreateTeamTagReq req) {
        TagDto tagDto = tagService.findDtoOrSave(req.tagName(), TagType.TEAM);
        return teamTagService.save(teamId, tagDto.id());
    }

    @Transactional
    public TeamMemberTagDto createTeamMemberTag(Long teamId, Long memberId, CreateTeamMemberTagReq req) {
        TagDto tagDto = tagService.findDtoOrSave(req.tagName(), TagType.TEAM_MEMBER);
        return teamMemberTagService.save(memberId, teamId, tagDto.id());
    }

    @Transactional
    public TeamTagDto updateTeamTag(Long tagId, Long teamId, UpdateTeamTagReq req) {
        deleteTeamTag(tagId, teamId);
        TagDto tagDto = tagService.findDtoOrSave(req.tagName(), TagType.TEAM);
        return teamTagService.save(teamId, tagDto.id());
    }

    @Transactional
    public TeamMemberTagDto updateTeamMemberTag(Long tagId, Long teamId, Long memberId, UpdateTeamMemberTagReq req) {
        deleteTeamMemberTag(tagId, teamId, memberId);
        TagDto tagDto = tagService.findDtoOrSave(req.tagName(), TagType.TEAM_MEMBER);
        return teamMemberTagService.save(memberId, teamId, tagDto.id());
    }

    @Transactional
    public Long deleteTeamTag(Long tagId, Long teamId) {
        Long teamTagId = teamTagService.findIdByTagIdAndTeamId(tagId, teamId);
        teamTagService.delete(teamTagId);
        return teamTagId;
    }

    @Transactional
    public Long deleteTeamMemberTag(Long tagId, Long teamId, Long memberId) {
        Long teamMemberTagId = teamMemberTagService.findIdByTagIdAndTeamIdAndMemberId(tagId, teamId, memberId);
        teamMemberTagService.delete(teamMemberTagId);
        return teamMemberTagId;
    }

}
