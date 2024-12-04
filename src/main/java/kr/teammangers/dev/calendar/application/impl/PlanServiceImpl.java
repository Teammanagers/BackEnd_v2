package kr.teammangers.dev.calendar.application.impl;

import kr.teammangers.dev.calendar.application.PlanService;
import kr.teammangers.dev.calendar.domain.Plan;
import kr.teammangers.dev.calendar.dto.PlanDto;
import kr.teammangers.dev.calendar.repository.PlanRepository;
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

}
