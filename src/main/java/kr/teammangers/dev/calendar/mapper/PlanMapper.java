package kr.teammangers.dev.calendar.mapper;

import kr.teammangers.dev.calendar.domain.entity.Plan;
import kr.teammangers.dev.calendar.dto.PlanDto;
import kr.teammangers.dev.calendar.dto.request.CreatePlanReq;
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
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "useYn", ignore = true)
    PlanDto toDto(CreatePlanReq req);

}
