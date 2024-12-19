package kr.teammangers.dev.team.presentation;

import jakarta.validation.Valid;
import kr.teammangers.dev.auth.infrastructure.security.AuthInfo;
import kr.teammangers.dev.global.common.response.ApiRes;
import kr.teammangers.dev.team.application.facade.TeamApiFacade;
import kr.teammangers.dev.team.dto.request.CreateTeamReq;
import kr.teammangers.dev.team.dto.request.JoinTeamReq;
import kr.teammangers.dev.team.dto.request.UpdateTeamReq;
import kr.teammangers.dev.team.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/team")
public class TeamController {

    private final TeamApiFacade teamApiFacade;

    @PostMapping
    public ApiRes<CreateTeamRes> createTeam(
            @AuthenticationPrincipal final AuthInfo auth,
            @RequestPart(name = "createTeam") @Valid final CreateTeamReq req,
            @RequestPart(name = "imageFile", required = false) final MultipartFile imageFile
    ) {
        CreateTeamRes result = teamApiFacade.createTeam(auth.memberDto().id(), req, imageFile);
        return ApiRes.onSuccess(result);
    }

    @GetMapping("/code")
    public ApiRes<GetTeamCodeRes> generateTeamCode() {
        GetTeamCodeRes result = teamApiFacade.generateTeamCode();
        return ApiRes.onSuccess(result);
    }

    @GetMapping
    public ApiRes<GetTeamRes> getTeamByTeamCode(
            @RequestParam("teamCode") final String teamCode
    ) {
        GetTeamRes result = teamApiFacade.getTeamByTeamCode(teamCode);
        return ApiRes.onSuccess(result);
    }

    @GetMapping("/list")
    public ApiRes<List<GetTeamRes>> getTeamListByMember(
            @AuthenticationPrincipal final AuthInfo auth
    ) {
        List<GetTeamRes> result = teamApiFacade.getTeamListByMemberId(auth.memberDto().id());
        return ApiRes.onSuccess(result);
    }

    @GetMapping("/{teamId}")
    public ApiRes<GetTeamRes> getTeamByTeamId(
            @PathVariable("teamId") final Long teamId
    ) {
        GetTeamRes result = teamApiFacade.getTeamByTeamId(teamId);
        return ApiRes.onSuccess(result);
    }

    @GetMapping("/{teamId}/member")
    public ApiRes<List<GetMemberRes>> getMemberListByTeam(
            @PathVariable("teamId") final Long teamId
    ) {
        List<GetMemberRes> result = teamApiFacade.getMemberListByTeamId(teamId);
        return ApiRes.onSuccess(result);
    }

    @PatchMapping
    public ApiRes<UpdateTeamRes> updateTeam(
            @RequestPart(name = "updateTeam") final UpdateTeamReq req,
            @RequestPart(name = "imageFile", required = false) final MultipartFile imageFile
    ) {
        UpdateTeamRes result = teamApiFacade.updateTeam(req, imageFile);
        return ApiRes.onSuccess(result);
    }

    @PostMapping("/{teamId}")
    public ApiRes<JoinTeamRes> joinTeam(
            @AuthenticationPrincipal final AuthInfo auth,
            @PathVariable("teamId") final Long teamId,
            @RequestBody final JoinTeamReq req
    ) {
        JoinTeamRes result = teamApiFacade.joinTeam(auth.memberDto().id(), teamId, req);
        return ApiRes.onSuccess(result);
    }

}
