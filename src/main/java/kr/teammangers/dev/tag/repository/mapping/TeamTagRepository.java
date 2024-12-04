package kr.teammangers.dev.tag.repository.mapping;

import kr.teammangers.dev.tag.domain.mapping.TeamTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamTagRepository extends JpaRepository<TeamTag, Long> {

    List<TeamTag> findAllByTeam_Id(Long teamId);

}
