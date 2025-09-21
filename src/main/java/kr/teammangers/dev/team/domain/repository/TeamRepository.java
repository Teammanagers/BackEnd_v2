package kr.teammangers.dev.team.domain.repository;

import kr.teammangers.dev.team.domain.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByCode(String code);

    @Query("SELECT DISTINCT t FROM Team t WHERE t.id = :id")
    Optional<Team> findDistinctById(@Param("id") Long id);
}
