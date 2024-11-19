package kr.teammangers.dev.team.repository;

import kr.teammangers.dev.team.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
