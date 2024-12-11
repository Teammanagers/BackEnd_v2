package kr.teammangers.dev.schedule.mapper;

import kr.teammangers.dev.schedule.dto.ScheduleDto;
import kr.teammangers.dev.schedule.dto.TimeRangeDto;
import kr.teammangers.dev.schedule.dto.TimeSlotDto;
import kr.teammangers.dev.schedule.dto.res.CreateScheduleRes;
import kr.teammangers.dev.schedule.dto.res.GetScheduleRes;
import kr.teammangers.dev.schedule.enums.DayOfWeek;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ScheduleResMapper {

    ScheduleResMapper SCHEDULE_RES_MAPPER = Mappers.getMapper(ScheduleResMapper.class);

    @Mapping(target = "createdTimeSlotId", source = "id")
    CreateScheduleRes toCreate(TimeSlotDto timeSlotDto);

    GetScheduleRes toGet(ScheduleDto scheduleDto);

    ScheduleDto toScheduleDto(DayOfWeek dayOfWeek, TimeRangeDto timeRangeDto);

}
