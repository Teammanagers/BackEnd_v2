package kr.teammangers.dev.schedule.dto;

import lombok.Builder;

import java.time.LocalTime;

@Builder
public record TimeRangeDto(
        LocalTime startTime,
        LocalTime endTime
) {
}
