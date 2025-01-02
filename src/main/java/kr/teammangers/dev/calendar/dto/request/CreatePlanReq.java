package kr.teammangers.dev.calendar.dto.request;

import java.time.LocalDate;

public record CreatePlanReq(
        LocalDate date,
        String title,
        String content
) {
}
