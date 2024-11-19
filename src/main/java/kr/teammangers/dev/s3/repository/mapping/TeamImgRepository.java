package kr.teammangers.dev.s3.repository.mapping;

import kr.teammangers.dev.team.domain.mapping.TeamImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamImgRepository extends JpaRepository<TeamImg, Long> {
}
