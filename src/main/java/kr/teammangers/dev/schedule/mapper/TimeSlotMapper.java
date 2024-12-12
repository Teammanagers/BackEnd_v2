package kr.teammangers.dev.schedule.mapper;

import kr.teammangers.dev.schedule.domain.TimeSlot;
import kr.teammangers.dev.schedule.dto.TimeSlotDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TimeSlotMapper {

    TimeSlotMapper TIME_SLOT_MAPPER = Mappers.getMapper(TimeSlotMapper.class);

    TimeSlotDto toDto(TimeSlot timeSlot);

}
