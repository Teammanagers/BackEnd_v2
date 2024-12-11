package kr.teammangers.dev.schedule.mapper;

import kr.teammangers.dev.schedule.dto.ScheduleDto;
import kr.teammangers.dev.schedule.dto.TimeRangeDto;
import kr.teammangers.dev.schedule.dto.TimeSlotDto;
import kr.teammangers.dev.schedule.dto.res.GetScheduleRes;
import kr.teammangers.dev.schedule.dto.res.UpdateScheduleRes;
import kr.teammangers.dev.schedule.enums.DayOfWeek;
import kr.teammangers.dev.schedule.util.TimeSlotBitUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScheduleResMapper {

    ScheduleResMapper SCHEDULE_RES_MAPPER = Mappers.getMapper(ScheduleResMapper.class);

    @Mapping(target = "updatedScheduleId", source = "schedule.id")
    UpdateScheduleRes toCreate(TimeSlotDto schedule);

    GetScheduleRes toGet(ScheduleDto scheduleDto);

    ScheduleDto toScheduleDto(DayOfWeek dayOfWeek, TimeRangeDto timeRangeDto);

    default List<ScheduleDto> toScheduleListFrom(TimeSlotDto timeSlotDto) {
        return timeSlotDto.dailySlots().entrySet().stream()
                .flatMap(entry -> {
                    DayOfWeek dayOfWeek = entry.getKey();
                    List<TimeRangeDto> timeRangeDtoList = TimeSlotBitUtils.getTimeRangeDtoList(entry.getValue());
                    return timeRangeDtoList.stream()
                            .map(timeRangeDto -> toScheduleDto(dayOfWeek, timeRangeDto));
                }).toList();
    }

}
