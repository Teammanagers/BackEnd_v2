package kr.teammangers.dev.team.repository.mapping;

import kr.teammangers.dev.team.domain.mapping.TeamManage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamManageRepository extends JpaRepository<TeamManage, Long> {

    boolean existsByTeam_IdAndMember_Id(Long teamId, Long memberId);

    List<TeamManage> findAllByMember_Id(Long memberId);

    List<TeamManage> findAllByTeam_Id(Long teamId);

    @Query("SELECT tm FROM TeamManage tm WHERE tm.id IN :teamManageIds")
    List<TeamManage> findAllByIds(@Param("teamManageIds") List<Long> teamManageIds);

    Optional<TeamManage> findByTeam_IdAndMember_Id(Long teamId, Long memberId);

}
