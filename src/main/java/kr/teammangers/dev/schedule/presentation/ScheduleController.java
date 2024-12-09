package kr.teammangers.dev.schedule.presentation;

import kr.teammangers.dev.auth.dto.AuthInfo;
import kr.teammangers.dev.common.payload.ApiRes;
import kr.teammangers.dev.schedule.application.ScheduleService;
import kr.teammangers.dev.schedule.dto.req.CreateScheduleReq;
import kr.teammangers.dev.schedule.dto.res.CreateScheduleRes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ApiRes<CreateScheduleRes> createSchedule(
            @AuthenticationPrincipal AuthInfo auth,
            @RequestBody final CreateScheduleReq req
    ) {
        CreateScheduleRes result = scheduleService.createSchedule(auth.memberDto().id(), req);
        return ApiRes.onSuccess(result);
    }

}
