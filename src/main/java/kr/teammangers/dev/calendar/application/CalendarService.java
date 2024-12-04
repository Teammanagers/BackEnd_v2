package kr.teammangers.dev.calendar.application;

import kr.teammangers.dev.calendar.dto.req.CreatePlanReq;
import kr.teammangers.dev.calendar.dto.req.DeletePlanReq;
import kr.teammangers.dev.calendar.dto.req.UpdatePlanReq;
import kr.teammangers.dev.calendar.dto.res.CreatePlanRes;
import kr.teammangers.dev.calendar.dto.res.DeletePlanRes;
import kr.teammangers.dev.calendar.dto.res.GetPlanRes;
import kr.teammangers.dev.calendar.dto.res.UpdatePlanRes;

import java.util.List;

public interface CalendarService {
    CreatePlanRes createPlan(CreatePlanReq req);

    List<GetPlanRes> getRecentPlanList(Long teamId, String yearMonth);

    UpdatePlanRes updatePlan(UpdatePlanReq req);

    DeletePlanRes deletePlan(DeletePlanReq req);

}
