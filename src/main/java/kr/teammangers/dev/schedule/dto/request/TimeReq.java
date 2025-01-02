package kr.teammangers.dev.schedule.dto.request;

public record TimeReq(
        int startHour,
        int startMinute,
        int endHour,
        int endMinute
) {
}
