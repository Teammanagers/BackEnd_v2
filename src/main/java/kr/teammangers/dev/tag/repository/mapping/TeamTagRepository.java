package kr.teammangers.dev.tag.repository.mapping;

import kr.teammangers.dev.team.domain.mapping.TeamTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamTagRepository extends JpaRepository<TeamTag, Long> {
}
