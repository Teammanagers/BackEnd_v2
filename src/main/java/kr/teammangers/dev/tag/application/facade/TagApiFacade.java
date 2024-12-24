package kr.teammangers.dev.tag.application.facade;

import kr.teammangers.dev.tag.application.service.TagService;
import kr.teammangers.dev.tag.application.service.TeamMemberTagService;
import kr.teammangers.dev.tag.application.service.TeamTagService;
import kr.teammangers.dev.tag.domain.enums.TagType;
import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.tag.dto.request.CreateTeamMemberTagReq;
import kr.teammangers.dev.tag.dto.request.CreateTeamTagReq;
import kr.teammangers.dev.tag.dto.request.DeleteTeamTagReq;
import kr.teammangers.dev.tag.dto.request.UpdateTeamTagReq;
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
    public DeleteTagRes deleteTeamTag(DeleteTeamTagReq req) {
        Long deletedId = deleteTeamTag(req.tagId(), req.teamId());
        return DeleteTagRes.builder()
                .deletedTargetId(deletedId)
                .build();
    }

    private Long deleteTeamTag(Long tagId, Long targetId) {
        Long teamTagId = teamTagService.findIdByTagIdAndTeamId(tagId, targetId);
        teamTagService.delete(teamTagId);
        return teamTagId;
    }

}
