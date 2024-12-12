package kr.teammangers.dev.schedule.dto.req;

public record TimeReq(
        int startHour,
        int startMinute,
        int endHour,
        int endMinute
) {
}
