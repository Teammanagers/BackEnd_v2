package kr.teammangers.dev.calendar.dto.req;

public record UpdatePlanReq(
        Long planId,
        String title,
        String content
) {
}
