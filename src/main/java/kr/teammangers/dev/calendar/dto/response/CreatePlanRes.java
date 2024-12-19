package kr.teammangers.dev.calendar.dto.response;

import lombok.Builder;

@Builder
public record CreatePlanRes(
        Long createdPlanId
) {
}
