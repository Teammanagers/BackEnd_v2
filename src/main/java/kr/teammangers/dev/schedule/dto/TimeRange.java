package kr.teammangers.dev.schedule.dto;

import java.time.LocalTime;

public record TimeRange(
        LocalTime startTime,
        LocalTime endTime
) {
}
