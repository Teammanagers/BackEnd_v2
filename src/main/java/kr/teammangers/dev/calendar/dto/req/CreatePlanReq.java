package kr.teammangers.dev.calendar.dto.req;

import java.time.LocalDate;

public record CreatePlanReq(
        Long teamId,
        LocalDate date,
        String title,
        String content
) {
}
