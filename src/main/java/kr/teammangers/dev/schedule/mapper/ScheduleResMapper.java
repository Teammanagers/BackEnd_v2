package kr.teammangers.dev.schedule.mapper;

import kr.teammangers.dev.schedule.dto.TimeSlotDto;
import kr.teammangers.dev.schedule.dto.res.CreateScheduleRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ScheduleResMapper {

    ScheduleResMapper SCHEDULE_RES_MAPPER = Mappers.getMapper(ScheduleResMapper.class);

    @Mapping(target = "createdTimeSlotId", source = "id")
    CreateScheduleRes toCreate(TimeSlotDto timeSlotDto);

}
