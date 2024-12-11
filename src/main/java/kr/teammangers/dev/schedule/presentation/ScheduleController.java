package kr.teammangers.dev.schedule.presentation;

import kr.teammangers.dev.auth.dto.AuthInfo;
import kr.teammangers.dev.common.payload.ApiRes;
import kr.teammangers.dev.schedule.application.ScheduleService;
import kr.teammangers.dev.schedule.dto.req.UpdateScheduleReq;
import kr.teammangers.dev.schedule.dto.res.UpdateScheduleRes;
import kr.teammangers.dev.schedule.dto.res.GetScheduleRes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/{teamId}/individual")
    public ApiRes<List<GetScheduleRes>> getSchedule(
            @AuthenticationPrincipal final AuthInfo auth,
            @PathVariable("teamId") final Long teamId
    ) {
        List<GetScheduleRes> result = scheduleService.getSchedule(auth.memberDto().id(), teamId);
        return ApiRes.onSuccess(result);
    }

    @GetMapping("/{teamId}")
    public ApiRes<List<GetScheduleRes>> getTeamSchedule(
            @PathVariable("teamId") final Long teamId
    ) {
        List<GetScheduleRes> result = scheduleService.getTeamSchedule(teamId);
        return ApiRes.onSuccess(result);
    }

    @PostMapping("/{teamId}")
    public ApiRes<UpdateScheduleRes> updateSchedule(
            @AuthenticationPrincipal final AuthInfo auth,
            @PathVariable("teamId") final Long teamId,
            @RequestBody final UpdateScheduleReq req
    ) {
        UpdateScheduleRes result = scheduleService.updateSchedule(auth.memberDto().id(), teamId, req);
        return ApiRes.onSuccess(result);
    }

}
