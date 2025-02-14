package kr.teammangers.dev.s3.domain.repository;

import kr.teammangers.dev.s3.domain.entity.TeamImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamImgRepository extends JpaRepository<TeamImg, Long> {

    Optional<TeamImg> findByTeam_Id(Long teamId);

    void deleteByTeam_Id(Long teamId);

}
