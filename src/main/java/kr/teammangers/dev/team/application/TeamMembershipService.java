package kr.teammangers.dev.team.application;

import kr.teammangers.dev.team.dto.req.JoinTeamReq;
import kr.teammangers.dev.team.dto.res.JoinTeamRes;
import org.springframework.transaction.annotation.Transactional;

public interface TeamMembershipService {
    @Transactional
    JoinTeamRes joinTeam(Long memberId, Long teamId, JoinTeamReq req);
}
