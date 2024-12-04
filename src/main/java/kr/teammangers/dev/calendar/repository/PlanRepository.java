package kr.teammangers.dev.calendar.repository;

import kr.teammangers.dev.calendar.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    @Query("SELECT p FROM Plan p WHERE p.teamId = :teamId ORDER BY p.createdAt DESC")
    List<Plan> findAllRecentPlanByTeamId(@Param("teamId") Long teamId);

}
