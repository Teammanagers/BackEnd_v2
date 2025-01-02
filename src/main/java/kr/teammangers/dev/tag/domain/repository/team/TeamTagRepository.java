package kr.teammangers.dev.tag.domain.repository.team;

import kr.teammangers.dev.tag.domain.entity.TeamTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamTagRepository extends JpaRepository<TeamTag, Long>, TeamTagRepositoryCustom {

    List<TeamTag> findAllByTeam_Id(Long teamId);

    Optional<TeamTag> findByTag_IdAndTeam_Id(Long tagId, Long teamId);

}
