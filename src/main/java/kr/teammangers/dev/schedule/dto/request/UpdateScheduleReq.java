package kr.teammangers.dev.schedule.dto.request;

import java.util.List;

public record UpdateScheduleReq(
        List<DayTimeReq> times
) {
}
