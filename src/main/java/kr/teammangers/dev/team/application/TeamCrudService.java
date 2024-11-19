package kr.teammangers.dev.team.application;

import kr.teammangers.dev.team.dto.req.CreateTeamReq;
import kr.teammangers.dev.team.dto.res.CreateTeamRes;
import org.springframework.web.multipart.MultipartFile;

public interface TeamCrudService {
    CreateTeamRes createTeam(Long authId, CreateTeamReq req, MultipartFile imageFile);
}
