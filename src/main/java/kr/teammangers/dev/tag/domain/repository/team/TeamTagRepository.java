package kr.teammangers.dev.tag.domain.repository.team;

import kr.teammangers.dev.tag.domain.entity.TeamTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamTagRepository extends JpaRepository<TeamTag, Long>, TeamTagRepositoryCustom {

    List<TeamTag> findAllByTeam_Id(Long teamId);

}
