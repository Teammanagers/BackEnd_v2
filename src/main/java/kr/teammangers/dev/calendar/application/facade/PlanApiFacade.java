package kr.teammangers.dev.calendar.application.facade;

import kr.teammangers.dev.calendar.application.service.PlanService;
import kr.teammangers.dev.calendar.dto.PlanDto;
import kr.teammangers.dev.calendar.dto.request.CreatePlanReq;
import kr.teammangers.dev.calendar.dto.request.UpdatePlanReq;
import kr.teammangers.dev.calendar.dto.response.GetPlanRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.teammangers.dev.calendar.mapper.PlanMapper.PLAN_MAPPER;
import static kr.teammangers.dev.calendar.mapper.PlanResMapper.PLAN_RES_MAPPER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlanApiFacade {

    private final PlanService planService;

    @Transactional
    public PlanDto createPlan(Long teamId, CreatePlanReq req) {
        PlanDto planDto = PLAN_MAPPER.toDto(teamId, req);
        return planService.save(planDto);
    }

    public List<GetPlanRes> getRecentPlanList(Long teamId, String yearMonth) {
        List<PlanDto> recentDtoList = yearMonth == null || yearMonth.isEmpty()
                ? planService.findAllRecentDtoByTeamId(teamId)
                : planService.findAllDtoByMonth(teamId, yearMonth);
        return recentDtoList.stream()
                .map(PLAN_RES_MAPPER::toGet)
                .toList();
    }

    @Transactional
    public PlanDto updatePlan(Long planId, UpdatePlanReq req) {
        return planService.update(planId, req);
    }

    @Transactional
    public Long deletePlan(Long planId) {
        planService.deleteByPlanId(planId);
        return planId;
    }

}
