package kr.teammangers.dev.schedule.dto.request;

import kr.teammangers.dev.schedule.domain.enums.DayOfWeek;

import java.util.List;

public record DayTimeReq(
        DayOfWeek dayOfWeek,
        List<TimeReq> timeRanges
) {
}
