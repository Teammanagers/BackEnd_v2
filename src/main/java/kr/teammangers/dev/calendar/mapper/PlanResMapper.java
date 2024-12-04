package kr.teammangers.dev.calendar.mapper;

import kr.teammangers.dev.calendar.dto.PlanDto;
import kr.teammangers.dev.calendar.dto.res.CreatePlanRes;
import kr.teammangers.dev.calendar.dto.res.GetPlanRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PlanResMapper {

    PlanResMapper PLAN_RES_MAPPER = Mappers.getMapper(PlanResMapper.class);

    @Mapping(target = "createdPlanId", source = "planDto.id")
    CreatePlanRes toCreate(PlanDto planDto);

    @Mapping(target = "planDto", source = "planDto")
    GetPlanRes toGet(PlanDto planDto);

}
