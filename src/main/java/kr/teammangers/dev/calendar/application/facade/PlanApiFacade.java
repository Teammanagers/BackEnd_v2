package kr.teammangers.dev.calendar.application.facade;

import kr.teammangers.dev.calendar.application.service.PlanService;
import kr.teammangers.dev.calendar.dto.PlanDto;
import kr.teammangers.dev.calendar.dto.request.CreatePlanReq;
import kr.teammangers.dev.calendar.dto.request.DeletePlanReq;
import kr.teammangers.dev.calendar.dto.request.UpdatePlanReq;
import kr.teammangers.dev.calendar.dto.response.CreatePlanRes;
import kr.teammangers.dev.calendar.dto.response.DeletePlanRes;
import kr.teammangers.dev.calendar.dto.response.GetPlanRes;
import kr.teammangers.dev.calendar.dto.response.UpdatePlanRes;
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
    public CreatePlanRes createPlan(CreatePlanReq req) {
        PlanDto planDto = PLAN_MAPPER.toDto(req);
        return PLAN_RES_MAPPER.toCreate(planService.save(planDto));
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
    public UpdatePlanRes updatePlan(UpdatePlanReq req) {
        PlanDto planDto = planService.update(req);
        return PLAN_RES_MAPPER.toUpdate(planDto);
    }

    @Transactional
    public DeletePlanRes deletePlan(DeletePlanReq req) {
        planService.deleteByPlanId(req.planId());
        return PLAN_RES_MAPPER.toDelete(req.planId());
    }

}
