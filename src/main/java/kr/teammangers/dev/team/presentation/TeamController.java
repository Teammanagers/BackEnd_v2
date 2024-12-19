package kr.teammangers.dev.team.presentation;

import jakarta.validation.Valid;
import kr.teammangers.dev.auth.dto.AuthInfo;
import kr.teammangers.dev.common.payload.ApiRes;
import kr.teammangers.dev.team.application.TeamCrudService;
import kr.teammangers.dev.team.application.TeamMembershipService;
import kr.teammangers.dev.team.application.TeamUtilService;
import kr.teammangers.dev.team.dto.req.CreateTeamReq;
import kr.teammangers.dev.team.dto.req.JoinTeamReq;
import kr.teammangers.dev.team.dto.req.UpdateTeamReq;
import kr.teammangers.dev.team.dto.res.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/team")
public class TeamController {

    private final TeamCrudService teamCrudService;
    private final TeamUtilService teamUtilService;
    private final TeamMembershipService teamMembershipService;

    @PostMapping
    public ApiRes<CreateTeamRes> createTeam(
            @AuthenticationPrincipal final AuthInfo auth,
            @RequestPart(name = "createTeam") @Valid final CreateTeamReq req,
            @RequestPart(name = "imageFile", required = false) final MultipartFile imageFile
    ) {
        CreateTeamRes result = teamCrudService.createTeam(auth.memberDto().id(), req, imageFile);
        return ApiRes.onSuccess(result);
    }

    @GetMapping("/code")
    public ApiRes<GetTeamCodeRes> generateTeamCode() {
        GetTeamCodeRes result = teamUtilService.generateTeamCode();
        return ApiRes.onSuccess(result);
    }

    @GetMapping
    public ApiRes<GetTeamRes> getTeamByTeamCode(
            @RequestParam("teamCode") final String teamCode
    ) {
        GetTeamRes result = teamCrudService.getTeamByTeamCode(teamCode);
        return ApiRes.onSuccess(result);
    }

    @GetMapping("/list")
    public ApiRes<List<GetTeamRes>> getTeamListByMember(
            @AuthenticationPrincipal final AuthInfo auth
    ) {
        List<GetTeamRes> result = teamCrudService.getTeamListByMemberId(auth.memberDto().id());
        return ApiRes.onSuccess(result);
    }

    @GetMapping("/{teamId}/member")
    public ApiRes<List<GetMemberRes>> getMemberListByTeam(
            @PathVariable("teamId") final Long teamId
    ) {
        List<GetMemberRes> result = teamCrudService.getMemberListByTeamId(teamId);
        return ApiRes.onSuccess(result);
    }

    @PatchMapping
    public ApiRes<UpdateTeamRes> updateTeam(
            @RequestPart(name = "updateTeam") final UpdateTeamReq req,
            @RequestPart(name = "imageFile", required = false) final MultipartFile imageFile
    ) {
        UpdateTeamRes result = teamCrudService.updateTeam(req, imageFile);
        return ApiRes.onSuccess(result);
    }

    @PostMapping("/{teamId}")
    public ApiRes<JoinTeamRes> joinTeam(
            @AuthenticationPrincipal final AuthInfo auth,
            @PathVariable("teamId") final Long teamId,
            @RequestBody final JoinTeamReq req
    ) {
        JoinTeamRes result = teamMembershipService.joinTeam(auth.memberDto().id(), teamId, req);
        return ApiRes.onSuccess(result);
    }

}
