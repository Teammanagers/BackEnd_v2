package kr.teammangers.dev.schedule.presentation;

import kr.teammangers.dev.auth.dto.AuthInfo;
import kr.teammangers.dev.common.payload.ApiRes;
import kr.teammangers.dev.schedule.application.ScheduleService;
import kr.teammangers.dev.schedule.dto.req.CreateScheduleReq;
import kr.teammangers.dev.schedule.dto.res.CreateScheduleRes;
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

    @PostMapping("/{teamId}")
    public ApiRes<CreateScheduleRes> createSchedule(
            @AuthenticationPrincipal final AuthInfo auth,
            @PathVariable("teamId") final Long teamId,
            @RequestBody final CreateScheduleReq req
    ) {
        CreateScheduleRes result = scheduleService.createSchedule(auth.memberDto().id(), teamId, req);
        return ApiRes.onSuccess(result);
    }

    @GetMapping("/{teamId}")
    public ApiRes<List<GetScheduleRes>> getSchedule(
            @AuthenticationPrincipal final AuthInfo auth,
            @PathVariable("teamId") final Long teamId
    ) {
        List<GetScheduleRes> result = scheduleService.getSchedule(auth.memberDto().id(), teamId);
        return ApiRes.onSuccess(result);
    }
}
