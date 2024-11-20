package kr.teammangers.dev.team.repository.mapping;

import kr.teammangers.dev.team.domain.mapping.TeamManage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamManageRepository extends JpaRepository<TeamManage, Long> {

    boolean existsByTeam_IdAndMember_Id(Long teamId, Long memberId);

}
