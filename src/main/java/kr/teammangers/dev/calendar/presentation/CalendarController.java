package kr.teammangers.dev.calendar.presentation;

import kr.teammangers.dev.calendar.application.CalendarService;
import kr.teammangers.dev.calendar.dto.req.CreatePlanReq;
import kr.teammangers.dev.calendar.dto.req.DeletePlanReq;
import kr.teammangers.dev.calendar.dto.req.UpdatePlanReq;
import kr.teammangers.dev.calendar.dto.res.CreatePlanRes;
import kr.teammangers.dev.calendar.dto.res.DeletePlanRes;
import kr.teammangers.dev.calendar.dto.res.GetPlanRes;
import kr.teammangers.dev.calendar.dto.res.UpdatePlanRes;
import kr.teammangers.dev.common.payload.ApiRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/calendar")
public class CalendarController {

    private final CalendarService calendarService;

    @PostMapping
    public ApiRes<CreatePlanRes> createPlan(
            @RequestBody final CreatePlanReq req
    ) {
        CreatePlanRes result = calendarService.createPlan(req);
        return ApiRes.onSuccess(result);
    }

    @GetMapping
    public ApiRes<List<GetPlanRes>> getRecentPlanList(
            @RequestParam("teamId") final Long teamId,
            @RequestParam(value = "yearMonth", required = false) final String yearMonth
    ) {
        List<GetPlanRes> result = calendarService.getRecentPlanList(teamId, yearMonth);
        return ApiRes.onSuccess(result);
    }

    @PatchMapping
    public ApiRes<UpdatePlanRes> updatePlan(
            @RequestBody final UpdatePlanReq req
    ) {
        UpdatePlanRes result = calendarService.updatePlan(req);
        return ApiRes.onSuccess(result);
    }

    @DeleteMapping
    public ApiRes<DeletePlanRes> deletePlan(
            @RequestBody final DeletePlanReq req
    ) {
        DeletePlanRes result = calendarService.deletePlan(req);
        return ApiRes.onSuccess(result);
    }

}
