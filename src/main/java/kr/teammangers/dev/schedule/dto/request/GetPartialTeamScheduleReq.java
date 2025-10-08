package kr.teammangers.dev.schedule.dto.request;

import java.util.List;

public record GetPartialTeamScheduleReq(
        List<Long> teamMemberIdList
) {
}
