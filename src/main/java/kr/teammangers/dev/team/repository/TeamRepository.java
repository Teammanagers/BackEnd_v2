package kr.teammangers.dev.team.repository;

import kr.teammangers.dev.team.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByCode(String code);

}
