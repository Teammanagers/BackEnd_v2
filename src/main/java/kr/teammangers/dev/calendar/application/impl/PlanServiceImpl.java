package kr.teammangers.dev.calendar.application.impl;

import kr.teammangers.dev.calendar.application.PlanService;
import kr.teammangers.dev.calendar.domain.Plan;
import kr.teammangers.dev.calendar.dto.PlanDto;
import kr.teammangers.dev.calendar.dto.req.UpdatePlanReq;
import kr.teammangers.dev.calendar.repository.PlanRepository;
import kr.teammangers.dev.common.payload.code.dto.enums.ErrorStatus;
import kr.teammangers.dev.common.payload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.teammangers.dev.calendar.mapper.PlanMapper.PLAN_MAPPER;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;

    @Override
    public PlanDto save(PlanDto planDto) {
        Plan plan = PLAN_MAPPER.toEntity(planDto);
        return PLAN_MAPPER.toDto(planRepository.save(plan));
    }

    @Override
    public List<PlanDto> findAllRecentDtoByTeamId(Long teamId) {
        return planRepository.findAllRecentPlanByTeamId(teamId).stream()
                .map(PLAN_MAPPER::toDto)
                .toList();
    }

    @Override
    public PlanDto update(UpdatePlanReq req) {
        Plan plan = findById(req.planId());
        plan.update(req);
        return PLAN_MAPPER.toDto(plan);
    }

    private Plan findById(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.PLAN_NOT_FOUND));
    }

}
