package kr.teammangers.dev.schedule.application;

import kr.teammangers.dev.schedule.dto.TimeSlotDto;
import kr.teammangers.dev.schedule.dto.req.CreateScheduleReq;
import kr.teammangers.dev.schedule.dto.res.CreateScheduleRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kr.teammangers.dev.schedule.mapper.ScheduleResMapper.SCHEDULE_RES_MAPPER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleService {

    private final TimeSlotService timeSlotService;

    @Transactional
    public CreateScheduleRes createSchedule(Long memberId, CreateScheduleReq req) {
        TimeSlotDto timeSlotDto = timeSlotService.save(memberId, req);
        return SCHEDULE_RES_MAPPER.toCreate(timeSlotDto);
    }

}
