package kr.teammangers.dev.calendar.application;

import kr.teammangers.dev.calendar.dto.PlanDto;

import java.util.List;

public interface PlanService {
    PlanDto save(PlanDto planDto);

    List<PlanDto> findAllRecentDtoByTeamId(Long teamId);
}
