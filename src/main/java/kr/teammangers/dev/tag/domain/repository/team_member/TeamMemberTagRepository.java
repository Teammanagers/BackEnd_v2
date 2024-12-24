package kr.teammangers.dev.tag.domain.repository.team_member;

import kr.teammangers.dev.tag.domain.entity.TeamMemberTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamMemberTagRepository extends JpaRepository<TeamMemberTag, Long> {

    List<TeamMemberTag> findAllByTeamMember_Id(Long teamMemberId);

    Optional<TeamMemberTag> findByTeamMember_IdAndTag_Id(Long teamMemberId, Long tagId);
}
