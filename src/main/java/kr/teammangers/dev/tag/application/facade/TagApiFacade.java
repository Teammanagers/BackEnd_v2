package kr.teammangers.dev.tag.application.facade;

import kr.teammangers.dev.tag.application.service.TagService;
import kr.teammangers.dev.tag.application.service.TeamMemberTagService;
import kr.teammangers.dev.tag.application.service.TeamTagService;
import kr.teammangers.dev.tag.domain.enums.TagType;
import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.tag.dto.request.*;
import kr.teammangers.dev.tag.dto.response.CreateTagRes;
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
    public CreateTagRes createTeamTag(CreateTeamTagReq req) {
        TagDto tagDto = tagService.findDtoOrSave(req.tagName(), TagType.TEAM);
        Long teamTagId = teamTagService.save(req.teamId(), tagDto.id());
        return CreateTagRes.builder()
                .createdTargetId(teamTagId)
                .build();
    }

    @Transactional
    public CreateTagRes createTeamMemberTag(CreateTeamMemberTagReq req) {
        TagDto tagDto = tagService.findDtoOrSave(req.tagName(), TagType.TEAM_MEMBER);

        Long teamMemberTagId = teamMemberTagService.save(req.memberId(), req.teamId(), tagDto.id());
        return CreateTagRes.builder()
                .createdTargetId(teamMemberTagId)
                .build();
    }

    @Transactional
    public UpdateTagRes updateTeamTag(UpdateTeamTagReq req) {
        deleteTeamTag(req.tagId(), req.teamId());
        TagDto tagDto = tagService.findDtoOrSave(req.tagName(), TagType.TEAM);
        Long newTeamTagId = teamTagService.save(req.teamId(), tagDto.id());
        return UpdateTagRes.builder()
                .updatedTargetId(newTeamTagId)
                .build();
    }

    @Transactional
    public UpdateTagRes updateTeamMemberTag(UpdateTeamMemberTagReq req) {
        deleteTeamMemberTag(req.tagId(), req.teamId(), req.memberId());
        TagDto tagDto = tagService.findDtoOrSave(req.tagName(), TagType.TEAM_MEMBER);
        Long newTeamMemberTagId = teamMemberTagService.save(req.memberId(), req.teamId(), tagDto.id());
        return UpdateTagRes.builder()
                .updatedTargetId(newTeamMemberTagId)
                .build();
    }

    @Transactional
    public DeleteTagRes deleteTeamTag(DeleteTeamTagReq req) {
        Long deletedId = deleteTeamTag(req.tagId(), req.teamId());
        return DeleteTagRes.builder()
                .deletedTargetId(deletedId)
                .build();
    }

    @Transactional
    public DeleteTagRes deleteTeamMemberTag(DeleteTeamMemberTagReq req) {
        Long deletedId = deleteTeamMemberTag(req.tagId(), req.teamId(), req.memberId());
        return DeleteTagRes.builder()
                .deletedTargetId(deletedId)
                .build();
    }
    
    private Long deleteTeamTag(Long tagId, Long teamId) {
        Long teamTagId = teamTagService.findIdByTagIdAndTeamId(tagId, teamId);
        teamTagService.delete(teamTagId);
        return teamTagId;
    }

    private Long deleteTeamMemberTag(Long tagId, Long teamId, Long memberId) {
        Long teamMemberTagId = teamMemberTagService.findIdByTagIdAndTeamIdAndMemberId(tagId, teamId, memberId);
        teamMemberTagService.delete(teamMemberTagId);
        return teamMemberTagId;
    }

}
