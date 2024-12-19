package kr.teammangers.dev.schedule.dto.response;

import kr.teammangers.dev.schedule.dto.ScheduleDto;
import lombok.Builder;

@Builder
public record GetScheduleRes(
        ScheduleDto scheduleDto
) {
}
