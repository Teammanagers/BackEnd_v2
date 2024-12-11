package kr.teammangers.dev.schedule.application;

import kr.teammangers.dev.schedule.dto.ScheduleDto;
import kr.teammangers.dev.schedule.dto.TimeRangeDto;
import kr.teammangers.dev.schedule.dto.TimeSlotDto;
import kr.teammangers.dev.schedule.dto.req.CreateScheduleReq;
import kr.teammangers.dev.schedule.dto.res.CreateScheduleRes;
import kr.teammangers.dev.schedule.dto.res.GetScheduleRes;
import kr.teammangers.dev.schedule.enums.DayOfWeek;
import kr.teammangers.dev.schedule.util.TimeSlotBitUtils;
import kr.teammangers.dev.team.application.base.TeamManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.teammangers.dev.schedule.mapper.ScheduleResMapper.SCHEDULE_RES_MAPPER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleService {

    private final TimeSlotService timeSlotService;
    private final TeamManageService teamManageService;

    @Transactional
    public CreateScheduleRes createSchedule(Long memberId, Long teamId, CreateScheduleReq req) {
        Long teamManageId = teamManageService.findIdByTeamIdAndMemberId(teamId, memberId);
        TimeSlotDto timeSlotDto = timeSlotService.save(teamManageId, req);
        return SCHEDULE_RES_MAPPER.toCreate(timeSlotDto);
    }

    public List<GetScheduleRes> getSchedule(Long memberId, Long teamId) {
        Long teamManageId = teamManageService.findIdByTeamIdAndMemberId(teamId, memberId);
        TimeSlotDto timeSlotDto = timeSlotService.findDtoByTeamManageId(teamManageId);
        List<ScheduleDto> scheduleDtoList = timeSlotDto.dailySlots().entrySet().stream()
                .flatMap(entry -> {
                    DayOfWeek dayOfWeek = entry.getKey();
                    List<TimeRangeDto> timeRangeDtoList = TimeSlotBitUtils.getTimeRangeDtoList(entry.getValue());
                    return timeRangeDtoList.stream()
                            .map(timeRangeDto -> SCHEDULE_RES_MAPPER.toScheduleDto(dayOfWeek, timeRangeDto));
                })
                .toList();
        return scheduleDtoList.stream()
                .map(SCHEDULE_RES_MAPPER::toGet)
                .toList();
    }

}
