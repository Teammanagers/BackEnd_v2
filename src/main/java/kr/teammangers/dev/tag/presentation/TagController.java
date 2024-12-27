package kr.teammangers.dev.tag.presentation;

import kr.teammangers.dev.global.common.response.ApiRes;
import kr.teammangers.dev.tag.application.facade.TagApiFacade;
import kr.teammangers.dev.tag.dto.TeamMemberTagDto;
import kr.teammangers.dev.tag.dto.TeamTagDto;
import kr.teammangers.dev.tag.dto.request.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/tag")
public class TagController {

    private final TagApiFacade tagApiFacade;

    @PostMapping("/team/{teamId}")
    public ApiRes<TeamTagDto> createTeamTag(
            @PathVariable("teamId") final Long teamId,
            @RequestBody final CreateTeamTagReq req
    ) {
        TeamTagDto result = tagApiFacade.createTeamTag(teamId, req);
        return ApiRes.onSuccess(result);
    }

    @PostMapping("/team/{teamId}/member/{memberId}")
    public ApiRes<TeamMemberTagDto> createTeamMemberTag(
            @PathVariable("teamId") final Long teamId,
            @PathVariable("memberId") final Long memberId,
            @RequestBody final CreateTeamMemberTagReq req
    ) {
        TeamMemberTagDto result = tagApiFacade.createTeamMemberTag(teamId, memberId, req);
        return ApiRes.onSuccess(result);
    }

    @PatchMapping("/{tagId}/team/{teamId}")
    public ApiRes<TeamTagDto> updateTeamTag(
            @PathVariable("tagId") final Long tagId,
            @PathVariable("teamId") final Long teamId,
            @RequestBody final UpdateTeamTagReq req
    ) {
        TeamTagDto result = tagApiFacade.updateTeamTag(tagId, teamId, req);
        return ApiRes.onSuccess(result);
    }

    @PatchMapping("/{tagId}/team/{teamId}/member/{memberId}")
    public ApiRes<TeamMemberTagDto> updateTeamMemberTag(
            @PathVariable("tagId") final Long tagId,
            @PathVariable("teamId") final Long teamId,
            @PathVariable("memberId") final Long memberId,
            @RequestBody final UpdateTeamMemberTagReq req
    ) {
        TeamMemberTagDto result = tagApiFacade.updateTeamMemberTag(tagId, teamId, memberId, req);
        return ApiRes.onSuccess(result);
    }

    @DeleteMapping("/{tagId}/team/{teamId}")
    public ApiRes<Long> deleteTeamTag(
            @PathVariable("tagId") final Long tagId,
            @PathVariable("teamId") final Long teamId
    ) {
        Long result = tagApiFacade.deleteTeamTag(tagId, teamId);
        return ApiRes.onSuccess(result);
    }

    @DeleteMapping("/{tagId}/team/{teamId}/member/{memberId}")
    public ApiRes<Long> deleteTeamMemberTag(
            @PathVariable("tagId") final Long tagId,
            @PathVariable("teamId") final Long teamId,
            @PathVariable("memberId") final Long memberId
    ) {
        Long result = tagApiFacade.deleteTeamMemberTag(tagId, teamId, memberId);
        return ApiRes.onSuccess(result);
    }

}
