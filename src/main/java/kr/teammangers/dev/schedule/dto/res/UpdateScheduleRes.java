package kr.teammangers.dev.schedule.dto.res;

import lombok.Builder;

@Builder
public record UpdateScheduleRes(
        Long updatedScheduleId
) {
}
