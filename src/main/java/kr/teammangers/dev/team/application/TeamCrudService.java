package kr.teammangers.dev.team.application;

import kr.teammangers.dev.team.dto.req.CreateTeamReq;
import kr.teammangers.dev.team.dto.res.CreateTeamRes;
import kr.teammangers.dev.team.dto.res.GetTeamRes;
import org.springframework.web.multipart.MultipartFile;

public interface TeamCrudService {
    CreateTeamRes createTeam(Long authId, CreateTeamReq req, MultipartFile imageFile);

    GetTeamRes getTeamByTeamCode(String teamCode);
}
