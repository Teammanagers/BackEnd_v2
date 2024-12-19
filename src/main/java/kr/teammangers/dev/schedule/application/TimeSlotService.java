package kr.teammangers.dev.schedule.application;

import kr.teammangers.dev.global.common.payload.exception.GeneralException;
import kr.teammangers.dev.schedule.domain.TimeSlot;
import kr.teammangers.dev.schedule.dto.TimeSlotDto;
import kr.teammangers.dev.schedule.dto.req.UpdateScheduleReq;
import kr.teammangers.dev.schedule.enums.DayOfWeek;
import kr.teammangers.dev.schedule.repository.TimeSlotRepository;
import kr.teammangers.dev.team.domain.mapping.TeamManage;
import kr.teammangers.dev.team.repository.TeamRepository;
import kr.teammangers.dev.team.repository.mapping.TeamManageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static kr.teammangers.dev.global.common.payload.code.dto.enums.ErrorStatus.TEAM_MANAGE_NOT_FOUND;
import static kr.teammangers.dev.global.common.payload.code.dto.enums.ErrorStatus.TEAM_NOT_FOUND;
import static kr.teammangers.dev.schedule.mapper.TimeSlotMapper.TIME_SLOT_MAPPER;

@Service
@RequiredArgsConstructor
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;
    private final TeamManageRepository teamManageRepository;
    private final TeamRepository teamRepository;

    public TimeSlotDto findDtoByTeamIdAndMemberId(Long teamId, Long memberId) {
        TimeSlot timeSlot = findByTeamIdAndMemberId(teamId, memberId);
        return TIME_SLOT_MAPPER.toDto(timeSlot);
    }

    public TimeSlotDto findDtoByTeamId(Long teamId) {
        TimeSlot timeSlot = findByTeamId(teamId);
        return TIME_SLOT_MAPPER.toDto(timeSlot);
    }

    public List<TimeSlotDto> findDtoByTeamManageIds(List<Long> teamManageIds) {
        return teamManageRepository.findAllByIds(teamManageIds).stream()
                .map(TeamManage::getTimeSlot)
                .filter(TimeSlot::getIsConfigured)
                .map(TIME_SLOT_MAPPER::toDto)
                .toList();
    }

    public TimeSlotDto update(Long teamId, Long memberId, UpdateScheduleReq req) {
        TimeSlot teamSchedule = findByTeamId(teamId);
        TimeSlot memberSchedule = findByTeamIdAndMemberId(teamId, memberId);

        // 멤버 스케줄 업데이트
        req.times().forEach(dayTimeReq ->
                dayTimeReq.timeRanges().forEach(timeReq ->
                        memberSchedule.setTimeSlot(
                                dayTimeReq.dayOfWeek(),
                                timeReq.startHour(),
                                timeReq.startMinute(),
                                timeReq.endHour(),
                                timeReq.endMinute()
                        )
                )
        );
        TimeSlot savedSchedule = timeSlotRepository.save(memberSchedule);

        // 팀 스케줄 업데이트
        if (!teamSchedule.getIsConfigured()) {
            teamSchedule.updateConfig();
            teamSchedule.update(savedSchedule);
        } else updateTeamSchedule(teamSchedule, savedSchedule);

        // 한 번에 저장하고 반환
        timeSlotRepository.save(teamSchedule);
        return TIME_SLOT_MAPPER.toDto(savedSchedule);
    }

    private void updateTeamSchedule(TimeSlot teamSchedule, TimeSlot memberSchedule) {
        Arrays.stream(DayOfWeek.values())
                .forEach(day -> {
                    Long teamSlot = teamSchedule.getDailySlots().get(day);
                    Long memberSlot = memberSchedule.getDailySlots().get(day);
                    teamSchedule.getDailySlots().put(day, teamSlot & memberSlot);
                });
    }

    private TimeSlot findByTeamId(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new GeneralException(TEAM_NOT_FOUND))
                .getTimeSlot();
    }

    private TimeSlot findByTeamIdAndMemberId(Long teamId, Long memberId) {
        return teamManageRepository.findByTeam_IdAndMember_Id(teamId, memberId)
                .orElseThrow(() -> new GeneralException(TEAM_MANAGE_NOT_FOUND))
                .getTimeSlot();
    }

}
