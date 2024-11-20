package kr.teammangers.dev.team.presentation;

import jakarta.validation.Valid;
import kr.teammangers.dev.auth.dto.AuthInfo;
import kr.teammangers.dev.common.payload.ApiRes;
import kr.teammangers.dev.team.application.TeamCrudService;
import kr.teammangers.dev.team.application.TeamMembershipService;
import kr.teammangers.dev.team.application.TeamUtilService;
import kr.teammangers.dev.team.dto.req.CreateTeamReq;
import kr.teammangers.dev.team.dto.req.JoinTeamReq;
import kr.teammangers.dev.team.dto.res.CreateTeamRes;
import kr.teammangers.dev.team.dto.res.GetTeamRes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        return ApiRes.onSuccess(teamCrudService.createTeam(auth.memberDto().id(), req, imageFile));
    }

    @GetMapping("/code")
    public ApiRes<String> generateTeamCode() {
        return ApiRes.onSuccess(teamUtilService.generateTeamCode());
    }

    @GetMapping
    public ApiRes<GetTeamRes> getTeamByTeamCode(
            @RequestParam("teamCode") final String teamCode
    ) {
        GetTeamRes result = teamCrudService.getTeamByTeamCode(teamCode);
        return ApiRes.onSuccess(result);
    }

    @PostMapping("/{teamId}")
    public ApiRes<Void> joinTeam(
            @AuthenticationPrincipal final AuthInfo auth,
            @PathVariable("teamId") final Long teamId,
            @RequestBody final JoinTeamReq req
    ) {
        teamMembershipService.joinTeam(auth.memberDto().id(), teamId, req);
        return ApiRes.onSuccess();
    }

}
