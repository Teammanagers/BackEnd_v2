package kr.teammangers.dev.schedule.dto.req;

import kr.teammangers.dev.schedule.enums.DayOfWeek;

import java.util.List;

public record DayTimeReq(
        DayOfWeek dayOfWeek,
        List<TimeReq> timeRanges
) {
}
