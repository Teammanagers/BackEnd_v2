package kr.teammangers.dev.alarm.presentation;

import kr.teammangers.dev.alarm.application.AlarmService;
import kr.teammangers.dev.alarm.dto.response.GetAlarmsResponse;
import kr.teammangers.dev.auth.infrastructure.security.AuthInfo;
import kr.teammangers.dev.global.common.response.ApiRes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/alarm")
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping
    public ApiRes<GetAlarmsResponse> getAllAlarms(@AuthenticationPrincipal final AuthInfo authInfo) {

        GetAlarmsResponse result = alarmService.getAlarms(authInfo.memberDto().id());
        return ApiRes.onSuccess(result);
    }

    @PatchMapping("/{AlarmId}")
    public ApiRes<Void> readAlarm(@PathVariable Long AlarmId) {

        alarmService.readAlarm(AlarmId);
        return ApiRes.onSuccess();
    }
}
