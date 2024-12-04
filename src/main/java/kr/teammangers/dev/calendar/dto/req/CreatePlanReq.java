package kr.teammangers.dev.calendar.dto.req;

public record CreatePlanReq(
        Long teamId,
        String title,
        String content
) {
}
