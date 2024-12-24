package kr.teammangers.dev.schedule.application.facade;

import kr.teammangers.dev.schedule.application.service.TimeSlotService;
import kr.teammangers.dev.schedule.dto.ScheduleDto;
import kr.teammangers.dev.schedule.dto.TimeSlotDto;
import kr.teammangers.dev.schedule.dto.request.UpdateScheduleReq;
import kr.teammangers.dev.schedule.dto.response.GetScheduleRes;
import kr.teammangers.dev.schedule.dto.response.UpdateScheduleRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.teammangers.dev.schedule.mapper.ScheduleResMapper.SCHEDULE_RES_MAPPER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TimeSlotApiFacade {

    private final TimeSlotService timeSlotService;

    @Transactional
    public UpdateScheduleRes updateSchedule(Long memberId, Long teamId, UpdateScheduleReq req) {
        TimeSlotDto timeSlotDto = timeSlotService.update(teamId, memberId, req);
        return SCHEDULE_RES_MAPPER.toCreate(timeSlotDto);
    }

    public List<GetScheduleRes> getSchedule(Long memberId, Long teamId) {
        TimeSlotDto timeSlotDto = timeSlotService.findDtoByTeamIdAndMemberId(teamId, memberId);
        List<ScheduleDto> scheduleDtoList = SCHEDULE_RES_MAPPER.toScheduleListFrom(timeSlotDto);
        return scheduleDtoList.stream()
                .map(SCHEDULE_RES_MAPPER::toGet)
                .toList();
    }

    public List<GetScheduleRes> getTeamSchedule(Long teamId) {
        TimeSlotDto timeSlotDto = timeSlotService.findDtoByTeamId(teamId);
        List<ScheduleDto> scheduleDtoList = SCHEDULE_RES_MAPPER.toScheduleListFrom(timeSlotDto);
        return scheduleDtoList.stream()
                .map(SCHEDULE_RES_MAPPER::toGet)
                .toList();
    }

}
