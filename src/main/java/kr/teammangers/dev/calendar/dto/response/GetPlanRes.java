package kr.teammangers.dev.calendar.dto.response;

import kr.teammangers.dev.calendar.dto.PlanDto;
import lombok.Builder;

@Builder
public record GetPlanRes(
        PlanDto planDto
) {
}
