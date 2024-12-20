package kr.teammangers.dev.tag.application.facade;

import kr.teammangers.dev.tag.application.service.TagService;
import kr.teammangers.dev.tag.application.service.TeamTagService;
import kr.teammangers.dev.tag.domain.enums.TagType;
import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.tag.dto.request.CreateTagReq;
import kr.teammangers.dev.tag.dto.request.DeleteTagReq;
import kr.teammangers.dev.tag.dto.request.UpdateTagReq;
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

    @Transactional
    public CreateTagRes createTeamTag(CreateTagReq req) {
        TagDto tagDto = tagService.findDtoOrSave(req.tagName(), TagType.TEAM);
        Long teamTagId = teamTagService.save(req.targetId(), tagDto.id());
        return CreateTagRes.builder()
                .createdTargetId(teamTagId)
                .build();
    }

    @Transactional
    public UpdateTagRes updateTeamTag(UpdateTagReq req) {
        delete(req.tagId(), req.targetId());
        TagDto tagDto = tagService.findDtoOrSave(req.tagName(), TagType.TEAM);
        Long newTeamTagId = teamTagService.save(req.targetId(), tagDto.id());
        return UpdateTagRes.builder()
                .updatedTargetId(newTeamTagId)
                .build();
    }

    @Transactional
    public DeleteTagRes deleteTeamTag(DeleteTagReq req) {
        Long deletedId = delete(req.tagId(), req.targetId());
        return DeleteTagRes.builder()
                .deletedTargetId(deletedId)
                .build();
    }

    private Long delete(Long tagId, Long targetId) {
        Long teamTagId = teamTagService.findIdByTagIdAndTeamId(tagId, targetId);
        teamTagService.delete(teamTagId);
        return teamTagId;
    }
}
