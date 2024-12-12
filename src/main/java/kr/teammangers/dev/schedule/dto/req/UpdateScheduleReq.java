package kr.teammangers.dev.schedule.dto.req;

import java.util.List;

public record UpdateScheduleReq(
        List<DayTimeReq> times
) {
}
