package kr.teammangers.dev.schedule.dto;

import kr.teammangers.dev.schedule.domain.enums.DayOfWeek;
import lombok.Builder;

import java.util.Map;

@Builder
public record TimeSlotDto(
        Long id,
        Map<DayOfWeek, Long> dailySlots,
        Boolean isConfigured
) {
}
