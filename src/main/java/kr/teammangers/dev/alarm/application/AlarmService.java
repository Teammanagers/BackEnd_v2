package kr.teammangers.dev.alarm.application;

import kr.teammangers.dev.alarm.domain.entity.Alarm;
import kr.teammangers.dev.alarm.domain.enums.AlarmType;
import kr.teammangers.dev.alarm.domain.repository.AlarmRepository;
import kr.teammangers.dev.alarm.dto.AlarmDto;
import kr.teammangers.dev.alarm.dto.response.GetAlarmsResponse;
import kr.teammangers.dev.global.error.code.ErrorStatus;
import kr.teammangers.dev.global.error.exception.GeneralException;
import kr.teammangers.dev.member.domain.entity.Member;
import kr.teammangers.dev.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final MemberRepository memberRepository;

    public GetAlarmsResponse getAlarms(Long memberId) {

        List<AlarmDto> alarmDtoList = alarmRepository.findAllByMemberId(memberId)
                .stream()
                .map(AlarmDto::from)
                .toList();

        return new GetAlarmsResponse(alarmDtoList);
    }

    public boolean readAlarm(Long alarmId) {

        Alarm alarmToRead = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ALARM_NOT_FOUND));

        return alarmToRead.read();
    }

    public AlarmDto createTodoAwakeAlarm(Long todoId, Long memberId) {

        Member member = memberRepository.getReferenceById(memberId);

        Alarm newAlarm = Alarm.builder()
                .alarmType(AlarmType.TODO)
                .referenceId(todoId)
                .content("누군가가 " + member.getName() + "님을 깨웠어요. 할 일을 해주세요!")
                .isRead(false)
                .build();


        newAlarm.setMember(member);

        alarmRepository.save(newAlarm);

        return AlarmDto.from(newAlarm);

    }


}
