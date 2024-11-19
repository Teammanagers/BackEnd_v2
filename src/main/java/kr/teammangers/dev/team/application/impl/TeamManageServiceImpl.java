package kr.teammangers.dev.team.application.impl;

import kr.teammangers.dev.member.domain.Member;
import kr.teammangers.dev.member.repository.MemberRepository;
import kr.teammangers.dev.team.application.TeamMangeService;
import kr.teammangers.dev.team.domain.Team;
import kr.teammangers.dev.team.mapper.TeamManageMapper;
import kr.teammangers.dev.team.repository.TeamRepository;
import kr.teammangers.dev.team.repository.mapping.TeamManageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamManageServiceImpl implements TeamMangeService {

    private final TeamManageRepository teamManageRepository;
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    @Override
    public Long save(Long teamId, Long memberId) {
        Team team = teamRepository.getReferenceById(teamId);
        Member member = memberRepository.getReferenceById(memberId);
        return teamManageRepository.save(TeamManageMapper.TEAM_MANAGE_MAPPER.toEntity(team, member)).getId();
    }

}
