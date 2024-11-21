package kr.teammangers.dev.team.application.impl;

import kr.teammangers.dev.team.application.TeamUtilService;
import kr.teammangers.dev.team.dto.res.GetTeamCodeRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static kr.teammangers.dev.team.mapper.TeamResMapper.TEAM_RES_MAPPER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamUtilServiceImpl implements TeamUtilService {

    @Override
    public GetTeamCodeRes generateTeamCode() {
        String generatedCode = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8).toUpperCase();
        return TEAM_RES_MAPPER.toGetTeamCode(generatedCode);
    }

}
