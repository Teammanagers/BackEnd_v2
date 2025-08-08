package kr.teammangers.dev.alarm.domain.repository;

import kr.teammangers.dev.alarm.domain.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    List<Alarm> findAllByMemberId(Long memberId);
}
