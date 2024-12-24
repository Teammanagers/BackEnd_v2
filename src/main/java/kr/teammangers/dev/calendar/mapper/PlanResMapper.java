package kr.teammangers.dev.calendar.mapper;

import kr.teammangers.dev.calendar.dto.PlanDto;
import kr.teammangers.dev.calendar.dto.response.CreatePlanRes;
import kr.teammangers.dev.calendar.dto.response.DeletePlanRes;
import kr.teammangers.dev.calendar.dto.response.GetPlanRes;
import kr.teammangers.dev.calendar.dto.response.UpdatePlanRes;
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

    @Mapping(target = "updatedPlanId", source = "planDto.id")
    UpdatePlanRes toUpdate(PlanDto planDto);

    @Mapping(target = "deletedPlanId", source = "planId")
    DeletePlanRes toDelete(Long planId);
}
