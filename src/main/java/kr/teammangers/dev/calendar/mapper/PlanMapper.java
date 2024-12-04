package kr.teammangers.dev.calendar.mapper;

import kr.teammangers.dev.calendar.domain.Plan;
import kr.teammangers.dev.calendar.dto.PlanDto;
import kr.teammangers.dev.calendar.dto.req.CreatePlanReq;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PlanMapper {

    PlanMapper PLAN_MAPPER = Mappers.getMapper(PlanMapper.class);

    @Mapping(target = "id", ignore = true)
    Plan toEntity(PlanDto planDto);

    PlanDto toDto(Plan plan);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "title", source = "req.title")
    @Mapping(target = "content", source = "req.content")
    @Mapping(target = "teamId", source = "req.teamId")
    PlanDto toDto(CreatePlanReq req);

}
