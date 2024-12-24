package kr.teammangers.dev.tag.presentation;

import kr.teammangers.dev.global.common.response.ApiRes;
import kr.teammangers.dev.tag.application.facade.TagApiFacade;
import kr.teammangers.dev.tag.dto.request.*;
import kr.teammangers.dev.tag.dto.response.CreateTagRes;
import kr.teammangers.dev.tag.dto.response.DeleteTagRes;
import kr.teammangers.dev.tag.dto.response.UpdateTagRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/tag")
public class TagController {

    private final TagApiFacade tagApiFacade;

    @PostMapping("/team")
    public ApiRes<CreateTagRes> createTeamTag(
            @RequestBody final CreateTeamTagReq req
    ) {
        CreateTagRes result = tagApiFacade.createTeamTag(req);
        return ApiRes.onSuccess(result);
    }

    @PostMapping("/team-member")
    public ApiRes<CreateTagRes> createTeamMemberTag(
            @RequestBody final CreateTeamMemberTagReq req
    ) {
        CreateTagRes result = tagApiFacade.createTeamMemberTag(req);
        return ApiRes.onSuccess(result);
    }

    @PatchMapping("/team")
    public ApiRes<UpdateTagRes> updateTeamTag(
            @RequestBody final UpdateTeamTagReq req
    ) {
        UpdateTagRes result = tagApiFacade.updateTeamTag(req);
        return ApiRes.onSuccess(result);
    }

    @PatchMapping("/team-member")
    public ApiRes<UpdateTagRes> updateTeamMemberTag(
            @RequestBody final UpdateTeamMemberTagReq req
    ) {
        UpdateTagRes result = tagApiFacade.updateTeamMemberTag(req);
        return ApiRes.onSuccess(result);
    }

    @DeleteMapping("/team")
    public ApiRes<DeleteTagRes> deleteTeamTag(
            @RequestBody final DeleteTeamTagReq req
    ) {
        DeleteTagRes result = tagApiFacade.deleteTeamTag(req);
        return ApiRes.onSuccess(result);
    }

    @DeleteMapping("/team-member")
    public ApiRes<DeleteTagRes> deleteTeamMemberTag(
            @RequestBody final DeleteTeamMemberTagReq req
    ) {
        DeleteTagRes result = tagApiFacade.deleteTeamMemberTag(req);
        return ApiRes.onSuccess(result);
    }

}
