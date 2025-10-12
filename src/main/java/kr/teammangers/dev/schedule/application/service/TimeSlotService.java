package kr.teammangers.dev.schedule.application.service;

import kr.teammangers.dev.global.error.exception.GeneralException;
import kr.teammangers.dev.schedule.domain.entity.TimeSlot;
import kr.teammangers.dev.schedule.dto.TimeSlotDto;
import kr.teammangers.dev.schedule.dto.request.UpdateScheduleReq;
import kr.teammangers.dev.schedule.domain.enums.DayOfWeek;
import kr.teammangers.dev.schedule.domain.repository.TimeSlotRepository;
import kr.teammangers.dev.team.application.service.TeamMemberService;
import kr.teammangers.dev.team.domain.entity.TeamMember;
import kr.teammangers.dev.team.domain.repository.TeamRepository;
import kr.teammangers.dev.team.domain.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static kr.teammangers.dev.global.error.code.ErrorStatus.TEAM_MEMBER_NOT_FOUND;
import static kr.teammangers.dev.global.error.code.ErrorStatus.TEAM_NOT_FOUND;
import static kr.teammangers.dev.schedule.mapper.TimeSlotMapper.TIME_SLOT_MAPPER;

@Service
@RequiredArgsConstructor
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamRepository teamRepository;

    private final TeamMemberService teamMemberService;

    public TimeSlotDto findDtoByTeamIdAndMemberId(Long teamId, Long memberId) {
        TimeSlot timeSlot = findByTeamIdAndMemberId(teamId, memberId);
        return TIME_SLOT_MAPPER.toDto(timeSlot);
    }

    public TimeSlotDto findDtoByTeamId(Long teamId) {
        TimeSlot timeSlot = findByTeamId(teamId);
        return TIME_SLOT_MAPPER.toDto(timeSlot);
    }

    public List<TimeSlotDto> findDtoByTeamMemberIds(List<Long> teamMemberIds) {
        return teamMemberRepository.findAllByIds(teamMemberIds).stream()
                .map(TeamMember::getTimeSlot)
                .filter(TimeSlot::getIsConfigured)
                .map(TIME_SLOT_MAPPER::toDto)
                .toList();
    }

    public TimeSlotDto update(Long teamId, Long memberId, UpdateScheduleReq req) {
        TimeSlot memberSchedule = findByTeamIdAndMemberId(teamId, memberId);

        // 멤버 스케줄 업데이트
        memberSchedule.resetTimeSlot();
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

        if (!memberSchedule.getIsConfigured()) {
            memberSchedule.updateConfig();
        }
        TimeSlot savedSchedule = timeSlotRepository.save(memberSchedule);

        // 팀 스케줄 업데이트
        updateTeamSchedule(teamId);
//        if (!teamSchedule.getIsConfigured()) {
//            teamSchedule.updateConfig();
//            teamSchedule.update(savedSchedule.getDailySlots());
//        } else updateTeamSchedule(teamSchedule, savedSchedule);

        return TIME_SLOT_MAPPER.toDto(savedSchedule);
    }

    public TimeSlotDto findPartialDtoByTeamMemberIds(List<Long> teamMemberIds) {
        List<TimeSlot> memberScheduleList = teamMemberIds.stream()
                .map(this::findByTeamMemberId).toList();

        Map<DayOfWeek, Long> combinedDailySlots = combineTimeSlots(memberScheduleList);

        return TIME_SLOT_MAPPER.toDto(combinedDailySlots);
    }

    private TimeSlotDto updateTeamSchedule(Long teamId) {
        List<TimeSlot> memberScheduleList = teamMemberRepository.findAllByTeam_Id(teamId)
                .stream()
                .map(TeamMember::getTimeSlot)
                .filter(TimeSlot::getIsConfigured)
                .toList();

        Map<DayOfWeek, Long> combinedDailySlots = combineTimeSlots(memberScheduleList);

        TimeSlot teamSchedule = findByTeamId(teamId);
        teamSchedule.update(combinedDailySlots);

        timeSlotRepository.save(teamSchedule);
        return TIME_SLOT_MAPPER.toDto(teamSchedule);
    }

    private Map<DayOfWeek, Long> combineTimeSlots(List<TimeSlot> timeSlotList) {
        Map<DayOfWeek, Long> dailySlots = new EnumMap<>(DayOfWeek.class);

        Arrays.stream(DayOfWeek.values())
                .forEach(day -> {
                    long combinedBits = -1L;
                    for (TimeSlot timeSlot : timeSlotList) {
                        Long bits = timeSlot.getDailySlots().get(day);
                        if (bits != null) combinedBits = combinedBits & bits;
                    }
                    dailySlots.put(day, combinedBits);
                });

        return dailySlots;
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
        return teamMemberRepository.findByTeam_IdAndMember_Id(teamId, memberId)
                .orElseThrow(() -> new GeneralException(TEAM_MEMBER_NOT_FOUND))
                .getTimeSlot();
    }

    private TimeSlot findByTeamMemberId(Long teamMemberId) {
        return teamMemberRepository.findById(teamMemberId)
                .orElseThrow(() -> new GeneralException(TEAM_MEMBER_NOT_FOUND))
                .getTimeSlot();
    }

}
