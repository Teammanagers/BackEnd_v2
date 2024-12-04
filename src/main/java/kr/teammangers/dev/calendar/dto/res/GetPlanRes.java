package kr.teammangers.dev.calendar.dto.res;

import kr.teammangers.dev.calendar.dto.PlanDto;
import lombok.Builder;

@Builder
public record GetPlanRes(
        PlanDto planDto
) {
}
