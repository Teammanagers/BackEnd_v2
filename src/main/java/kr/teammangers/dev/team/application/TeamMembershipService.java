package kr.teammangers.dev.team.application;

import kr.teammangers.dev.team.dto.req.JoinTeamReq;
import org.springframework.transaction.annotation.Transactional;

public interface TeamMembershipService {
    @Transactional
    void joinTeam(Long memberId, Long teamId, JoinTeamReq req);
}
