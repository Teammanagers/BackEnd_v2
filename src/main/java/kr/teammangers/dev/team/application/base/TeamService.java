package kr.teammangers.dev.team.application.base;

import kr.teammangers.dev.team.dto.TeamDto;
import kr.teammangers.dev.team.dto.req.CreateTeamReq;

public interface TeamService {

    TeamDto save(CreateTeamReq req, Long rootFolderId);

    TeamDto findDtoByTeamCode(String teamCode);

    TeamDto findDtoById(Long id);

    void validateTeamAdmin(Long teamId, Long memberId);
}
