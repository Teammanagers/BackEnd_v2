package kr.teammangers.dev.tag.repository.mapping;

import kr.teammangers.dev.tag.domain.mapping.GrantedRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GrantedRoleRepository extends JpaRepository<GrantedRole, Long> {

    List<GrantedRole> findAllByTeamMember_Id(Long teamMemberId);
}
