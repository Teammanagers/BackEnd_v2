package kr.teammangers.dev.calendar.application.impl;

import kr.teammangers.dev.calendar.application.CalendarService;
import kr.teammangers.dev.calendar.application.PlanService;
import kr.teammangers.dev.calendar.dto.PlanDto;
import kr.teammangers.dev.calendar.dto.req.CreatePlanReq;
import kr.teammangers.dev.calendar.dto.req.DeletePlanReq;
import kr.teammangers.dev.calendar.dto.req.UpdatePlanReq;
import kr.teammangers.dev.calendar.dto.res.CreatePlanRes;
import kr.teammangers.dev.calendar.dto.res.DeletePlanRes;
import kr.teammangers.dev.calendar.dto.res.GetPlanRes;
import kr.teammangers.dev.calendar.dto.res.UpdatePlanRes;
import kr.teammangers.dev.calendar.mapper.PlanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.teammangers.dev.calendar.mapper.PlanResMapper.PLAN_RES_MAPPER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {

    private final PlanService planService;

    @Override
    @Transactional
    public CreatePlanRes createPlan(CreatePlanReq req) {
        PlanDto planDto = PlanMapper.PLAN_MAPPER.toDto(req);
        return PLAN_RES_MAPPER.toCreate(planService.save(planDto));
    }

    @Override
    public List<GetPlanRes> getRecentPlanList(Long teamId) {
        List<PlanDto> recentDtoList = planService.findAllRecentDtoByTeamId(teamId);
        return recentDtoList.stream()
                .map(PLAN_RES_MAPPER::toGet)
                .toList();
    }

    @Override
    @Transactional
    public UpdatePlanRes updatePlan(UpdatePlanReq req) {
        PlanDto planDto = planService.update(req);
        return PLAN_RES_MAPPER.toUpdate(planDto);
    }

    @Override
    @Transactional
    public DeletePlanRes deletePlan(DeletePlanReq req) {
        planService.deleteByPlanId(req.planId());
        return PLAN_RES_MAPPER.toDelete(req.planId());
    }

}
