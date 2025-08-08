package kr.teammangers.dev.alarm.dto;

import kr.teammangers.dev.alarm.domain.entity.Alarm;
import kr.teammangers.dev.alarm.domain.enums.AlarmType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AlarmDto(
        Long id,
        AlarmType alarmType,
        Long referenceId,
        String content,
        Boolean read,
        LocalDateTime dateTime
) {
    public static AlarmDto from(Alarm alarm) {
        return AlarmDto.builder()
                .id(alarm.getId())
                .alarmType(alarm.getAlarmType())
                .referenceId(alarm.getReferenceId())
                .content(alarm.getContent())
                .read(alarm.getRead())
                .dateTime(alarm.getCreatedAt())
                .build();
    }
}
