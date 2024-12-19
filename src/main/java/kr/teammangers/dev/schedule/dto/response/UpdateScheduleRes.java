package kr.teammangers.dev.schedule.dto.response;

import lombok.Builder;

@Builder
public record UpdateScheduleRes(
        Long updatedScheduleId
) {
}
