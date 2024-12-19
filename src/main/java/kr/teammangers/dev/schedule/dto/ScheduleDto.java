package kr.teammangers.dev.schedule.dto;

import kr.teammangers.dev.schedule.domain.enums.DayOfWeek;
import lombok.Builder;

@Builder
public record ScheduleDto(
        DayOfWeek dayOfWeek,
        TimeRangeDto timeRangeDto
) {
}
