package kr.teammangers.dev.calendar.dto.request;

public record UpdatePlanReq(
        Long planId,
        String title,
        String content
) {
}
