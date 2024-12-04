package kr.teammangers.dev.calendar.application;

import kr.teammangers.dev.calendar.dto.PlanDto;
import kr.teammangers.dev.calendar.dto.req.UpdatePlanReq;

import java.util.List;

public interface PlanService {
    PlanDto save(PlanDto planDto);

    List<PlanDto> findAllRecentDtoByTeamId(Long teamId);

    List<PlanDto> findAllDtoByMonth(Long teamId, String yearMonth);

    PlanDto update(UpdatePlanReq req);

    void deleteByPlanId(Long planId);

}
