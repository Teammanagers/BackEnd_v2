package kr.teammangers.dev.s3.repository.mapping;

import kr.teammangers.dev.s3.domain.mapping.TeamImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamImgRepository extends JpaRepository<TeamImg, Long> {

    Optional<TeamImg> findByTeam_Id(Long teamId);

}
