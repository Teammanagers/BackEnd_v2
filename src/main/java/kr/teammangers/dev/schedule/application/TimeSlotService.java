package kr.teammangers.dev.schedule.application;

import kr.teammangers.dev.common.payload.exception.GeneralException;
import kr.teammangers.dev.schedule.domain.TimeSlot;
import kr.teammangers.dev.schedule.dto.TimeSlotDto;
import kr.teammangers.dev.schedule.dto.req.CreateScheduleReq;
import kr.teammangers.dev.schedule.repository.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kr.teammangers.dev.common.payload.code.dto.enums.ErrorStatus.TIME_SLOT_NOT_FOUND;
import static kr.teammangers.dev.schedule.mapper.TimeSlotMapper.TIME_SLOT_MAPPER;

@Service
@RequiredArgsConstructor
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;

    public TimeSlotDto save(Long teamManageId, CreateScheduleReq req) {
        TimeSlot timeSlot = TimeSlot.builder()
                .teamManageId(teamManageId)
                .build();
        req.times().forEach(dayTimeReq ->
                dayTimeReq.timeRanges().forEach(timeReq ->
                        timeSlot.setTimeSlot(
                                dayTimeReq.dayOfWeek(),
                                timeReq.startHour(),
                                timeReq.startMinute(),
                                timeReq.endHour(),
                                timeReq.endMinute()
                        )
                )
        );
        return TIME_SLOT_MAPPER.toDto(timeSlotRepository.save(timeSlot));
    }

    public TimeSlotDto findDtoByTeamManageId(Long teamManageId) {
        return timeSlotRepository.findByTeamManageId(teamManageId)
                .map(TIME_SLOT_MAPPER::toDto)
                .orElseThrow(() -> new GeneralException(TIME_SLOT_NOT_FOUND));
    }

}
