package kr.teammangers.dev.calendar.application.service;

import kr.teammangers.dev.calendar.domain.entity.Plan;
import kr.teammangers.dev.calendar.dto.PlanDto;
import kr.teammangers.dev.calendar.dto.request.UpdatePlanReq;
import kr.teammangers.dev.calendar.domain.repository.PlanRepository;
import kr.teammangers.dev.global.error.code.ErrorStatus;
import kr.teammangers.dev.global.error.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static kr.teammangers.dev.calendar.mapper.PlanMapper.PLAN_MAPPER;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;

    public PlanDto save(PlanDto planDto) {
        Plan plan = PLAN_MAPPER.toEntity(planDto);
        return PLAN_MAPPER.toDto(planRepository.save(plan));
    }

    public List<PlanDto> findAllRecentDtoByTeamId(Long teamId) {
        return planRepository.findAllRecentPlanByTeamId(teamId).stream()
                .map(PLAN_MAPPER::toDto)
                .toList();
    }

    public List<PlanDto> findAllDtoByMonth(Long teamId, String yearMonth) {
        YearMonth parseYearMonth = YearMonth.parse(yearMonth);
        LocalDate startDate = parseYearMonth.atDay(1);
        LocalDate endDate = parseYearMonth.plusMonths(1).atDay(1);
        return planRepository.findAllPlanByMonth(teamId, startDate, endDate).stream()
                .map(PLAN_MAPPER::toDto)
                .toList();
    }

    public PlanDto update(UpdatePlanReq req) {
        Plan plan = findById(req.planId());
        plan.update(req);
        return PLAN_MAPPER.toDto(plan);
    }

    public void deleteByPlanId(Long planId) {
        planRepository.deleteById(planId);
    }

    private Plan findById(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.PLAN_NOT_FOUND));
    }

}
