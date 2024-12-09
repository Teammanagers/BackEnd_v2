package kr.teammangers.dev.schedule.dto.req;

import java.util.List;

public record CreateScheduleReq(
        List<DayTimeReq> times
) {
}
