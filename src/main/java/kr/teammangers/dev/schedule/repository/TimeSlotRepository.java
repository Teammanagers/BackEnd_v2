package kr.teammangers.dev.schedule.repository;

import kr.teammangers.dev.schedule.domain.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
}
