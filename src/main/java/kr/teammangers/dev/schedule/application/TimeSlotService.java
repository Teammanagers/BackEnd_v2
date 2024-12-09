package kr.teammangers.dev.schedule.application;

import kr.teammangers.dev.schedule.domain.TimeSlot;
import kr.teammangers.dev.schedule.dto.TimeSlotDto;
import kr.teammangers.dev.schedule.dto.req.CreateScheduleReq;
import kr.teammangers.dev.schedule.mapper.TimeSlotMapper;
import kr.teammangers.dev.schedule.repository.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;

    public TimeSlotDto save(Long memberId, CreateScheduleReq req) {
        TimeSlot timeSlot = TimeSlot.builder()
                .memberId(memberId)
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
        return TimeSlotMapper.TIME_SLOT_MAPPER.toDto(timeSlotRepository.save(timeSlot));
    }

}
