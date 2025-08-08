package kr.teammangers.dev.alarm.dto.response;

import kr.teammangers.dev.alarm.dto.AlarmDto;

import java.util.List;

public record GetAlarmsResponse(
        List<AlarmDto> alarms
) {
    public static GetAlarmsResponse of(List<AlarmDto> alarms) {
        return new GetAlarmsResponse(alarms);
    }
}
