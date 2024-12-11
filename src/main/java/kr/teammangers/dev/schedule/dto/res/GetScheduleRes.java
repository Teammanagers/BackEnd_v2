package kr.teammangers.dev.schedule.dto.res;

import kr.teammangers.dev.schedule.dto.ScheduleDto;
import lombok.Builder;

@Builder
public record GetScheduleRes(
        ScheduleDto scheduleDto
) {
}
