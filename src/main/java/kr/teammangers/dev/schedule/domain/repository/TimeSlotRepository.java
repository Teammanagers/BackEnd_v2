package kr.teammangers.dev.schedule.domain.repository;

import kr.teammangers.dev.schedule.domain.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

}
