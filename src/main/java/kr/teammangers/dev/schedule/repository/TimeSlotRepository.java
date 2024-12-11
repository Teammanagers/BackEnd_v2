package kr.teammangers.dev.schedule.repository;

import kr.teammangers.dev.schedule.domain.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

    Optional<TimeSlot> findByTeamManageId(Long teamManageId);
}
