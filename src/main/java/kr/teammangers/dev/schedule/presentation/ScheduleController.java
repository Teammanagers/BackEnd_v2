package kr.teammangers.dev.schedule.presentation;

import kr.teammangers.dev.auth.infrastructure.security.AuthInfo;
import kr.teammangers.dev.global.common.response.ApiRes;
import kr.teammangers.dev.schedule.application.facade.TimeSlotApiFacade;
import kr.teammangers.dev.schedule.dto.TimeSlotDto;
import kr.teammangers.dev.schedule.dto.request.UpdateScheduleReq;
import kr.teammangers.dev.schedule.dto.response.GetScheduleRes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/schedule")
public class ScheduleController {

    private final TimeSlotApiFacade timeSlotApiFacade;

    @GetMapping("/teams/{teamId}/my-schedules")
    public ApiRes<List<GetScheduleRes>> getSchedule(
            @AuthenticationPrincipal final AuthInfo auth,
            @PathVariable("teamId") final Long teamId
    ) {
        List<GetScheduleRes> result = timeSlotApiFacade.getSchedule(auth.memberDto().id(), teamId);
        return ApiRes.onSuccess(result);
    }

    @GetMapping("/teams/{teamId}")
    public ApiRes<List<GetScheduleRes>> getTeamSchedule(
            @PathVariable("teamId") final Long teamId
    ) {
        List<GetScheduleRes> result = timeSlotApiFacade.getTeamSchedule(teamId);
        return ApiRes.onSuccess(result);
    }

    @PostMapping("/teams/{teamId}")
    public ApiRes<TimeSlotDto> updateSchedule(
            @AuthenticationPrincipal final AuthInfo auth,
            @PathVariable("teamId") final Long teamId,
            @RequestBody final UpdateScheduleReq req
    ) {
        TimeSlotDto result = timeSlotApiFacade.updateSchedule(auth.memberDto().id(), teamId, req);
        return ApiRes.onSuccess(result);
    }

}
