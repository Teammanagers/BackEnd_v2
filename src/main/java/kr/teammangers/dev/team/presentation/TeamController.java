package kr.teammangers.dev.team.presentation;

import jakarta.validation.Valid;
import kr.teammangers.dev.auth.infrastructure.security.AuthInfo;
import kr.teammangers.dev.global.common.response.ApiRes;
import kr.teammangers.dev.team.application.facade.TeamApiFacade;
import kr.teammangers.dev.team.dto.TeamDto;
import kr.teammangers.dev.team.dto.request.CreateTeamReq;
import kr.teammangers.dev.team.dto.request.JoinTeamReq;
import kr.teammangers.dev.team.dto.request.UpdateTeamPasswordReq;
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
    public ApiRes<TeamDto> createTeam(
            @AuthenticationPrincipal final AuthInfo auth,
            @RequestPart(name = "createTeam") @Valid final CreateTeamReq req,
            @RequestPart(name = "imageFile", required = false) final MultipartFile imageFile
    ) {
        TeamDto result = teamApiFacade.createTeam(auth.memberDto().id(), req, imageFile);
        return ApiRes.onSuccess(result);
    }

    @GetMapping("/by-code")
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

    @GetMapping("/{teamId}/member-list")
    public ApiRes<List<GetMemberRes>> getMemberListByTeam(
            @PathVariable("teamId") final Long teamId
    ) {
        List<GetMemberRes> result = teamApiFacade.getMemberListByTeamId(teamId);
        return ApiRes.onSuccess(result);
    }

    @PatchMapping("/{teamId}/password")
    public ApiRes<TeamDto> updateTeamPassword(
            @PathVariable("teamId") final Long teamId,
            @RequestBody final UpdateTeamPasswordReq req
    ) {
        TeamDto result = teamApiFacade.updateTeamPassword(teamId, req);
        return ApiRes.onSuccess(result);
    }

    @PatchMapping("/{teamId}")
    public ApiRes<TeamDto> updateTeam(
            @PathVariable("teamId") final Long teamId,
            @RequestPart(name = "updateTeam") final UpdateTeamReq req,
            @RequestPart(name = "imageFile", required = false) final MultipartFile imageFile
    ) {
        TeamDto result = teamApiFacade.updateTeam(teamId, req, imageFile);
        return ApiRes.onSuccess(result);
    }

    @PatchMapping("/{teamId}/complete")
    public ApiRes<TeamDto> completeTeam(
            @PathVariable("teamId") final Long teamId
    ) {
        TeamDto result = teamApiFacade.completeTeam(teamId);
        return ApiRes.onSuccess(result);
    }

    @PostMapping("/{teamId}/join")
    public ApiRes<TeamDto> joinTeam(
            @AuthenticationPrincipal final AuthInfo auth,
            @PathVariable("teamId") final Long teamId,
            @RequestBody final JoinTeamReq req
    ) {
        TeamDto result = teamApiFacade.joinTeam(auth.memberDto().id(), teamId, req);
        return ApiRes.onSuccess(result);
    }

}
