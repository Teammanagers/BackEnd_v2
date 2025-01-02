package kr.teammangers.dev.team.domain.repository;

import kr.teammangers.dev.team.domain.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    boolean existsByTeam_IdAndMember_Id(Long teamId, Long memberId);

    List<TeamMember> findAllByMember_Id(Long memberId);

    List<TeamMember> findAllByTeam_Id(Long teamId);

    @Query("SELECT tm FROM TeamMember tm WHERE tm.id IN :teamMemberIds")
    List<TeamMember> findAllByIds(@Param("teamMemberIds") List<Long> teamMemberIds);

    Optional<TeamMember> findByTeam_IdAndMember_Id(Long teamId, Long memberId);

}
