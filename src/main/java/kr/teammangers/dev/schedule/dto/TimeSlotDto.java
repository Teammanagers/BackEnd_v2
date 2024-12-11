package kr.teammangers.dev.schedule.dto;

import kr.teammangers.dev.schedule.enums.DayOfWeek;
import lombok.Builder;

import java.util.Map;

@Builder
public record TimeSlotDto(
        Long id,
        Map<DayOfWeek, Long> dailySlots,

        Long teamManageId
) {
}
