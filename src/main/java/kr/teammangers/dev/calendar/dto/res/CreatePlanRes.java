package kr.teammangers.dev.calendar.dto.res;

import lombok.Builder;

@Builder
public record CreatePlanRes(
        Long createdPlanId
) {
}
