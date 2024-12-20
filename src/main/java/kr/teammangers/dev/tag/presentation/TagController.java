package kr.teammangers.dev.tag.presentation;

import kr.teammangers.dev.global.common.response.ApiRes;
import kr.teammangers.dev.tag.application.facade.TagApiFacade;
import kr.teammangers.dev.tag.dto.request.CreateTagReq;
import kr.teammangers.dev.tag.dto.request.DeleteTagReq;
import kr.teammangers.dev.tag.dto.request.UpdateTagReq;
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
            @RequestBody final CreateTagReq req
    ) {
        CreateTagRes result = tagApiFacade.createTeamTag(req);
        return ApiRes.onSuccess(result);
    }

    @PatchMapping("/team")
    public ApiRes<UpdateTagRes> updateTeamTag(
            @RequestBody final UpdateTagReq req
    ) {
        UpdateTagRes result = tagApiFacade.updateTeamTag(req);
        return ApiRes.onSuccess(result);
    }

    @DeleteMapping("/team")
    public ApiRes<DeleteTagRes> deleteTeamTag(
            @RequestBody final DeleteTagReq req
    ) {
        DeleteTagRes result = tagApiFacade.deleteTeamTag(req);
        return ApiRes.onSuccess(result);
    }
}
