package kr.teammangers.dev.calendar.presentation;

import kr.teammangers.dev.calendar.application.facade.PlanApiFacade;
import kr.teammangers.dev.calendar.dto.PlanDto;
import kr.teammangers.dev.calendar.dto.request.CreatePlanReq;
import kr.teammangers.dev.calendar.dto.request.UpdatePlanReq;
import kr.teammangers.dev.calendar.dto.response.GetPlanRes;
import kr.teammangers.dev.global.common.response.ApiRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/calendar")
public class CalendarController {

    private final PlanApiFacade calendarService;

    @PostMapping("/teams/{teamId}")
    public ApiRes<PlanDto> createPlan(
            @PathVariable("teamId") final Long teamId,
            @RequestBody final CreatePlanReq req
    ) {
        PlanDto result = calendarService.createPlan(teamId, req);
        return ApiRes.onSuccess(result);
    }

    @GetMapping("/list")
    public ApiRes<List<GetPlanRes>> getRecentPlanList(
            @RequestParam("teamId") final Long teamId,
            @RequestParam(value = "yearMonth", required = false) final String yearMonth
    ) {
        List<GetPlanRes> result = calendarService.getRecentPlanList(teamId, yearMonth);
        return ApiRes.onSuccess(result);
    }

    @PatchMapping("/{planId}")
    public ApiRes<PlanDto> updatePlan(
            @PathVariable final Long planId,
            @RequestBody final UpdatePlanReq req
    ) {
        PlanDto result = calendarService.updatePlan(planId, req);
        return ApiRes.onSuccess(result);
    }

    @DeleteMapping("/{planId}")
    public ApiRes<Long> deletePlan(
            @PathVariable final Long planId
    ) {
        Long result = calendarService.deletePlan(planId);     // TODO: 본인 일정만 삭제하도록 수정 필요
        return ApiRes.onSuccess(result);
    }

}
