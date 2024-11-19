package kr.teammangers.dev.team.application.impl;

import kr.teammangers.dev.team.application.TeamUtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamUtilServiceImpl implements TeamUtilService {

    @Override
    public String generateTeamCode() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8).toUpperCase();
    }

}
