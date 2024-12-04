package kr.teammangers.dev.team.application.base.impl;

import kr.teammangers.dev.common.payload.exception.GeneralException;
import kr.teammangers.dev.member.domain.Member;
import kr.teammangers.dev.member.repository.MemberRepository;
import kr.teammangers.dev.team.application.base.TeamManageService;
import kr.teammangers.dev.team.domain.Team;
import kr.teammangers.dev.team.domain.mapping.TeamManage;
import kr.teammangers.dev.team.dto.TeamDto;
import kr.teammangers.dev.team.mapper.TeamManageMapper;
import kr.teammangers.dev.team.mapper.TeamMapper;
import kr.teammangers.dev.team.repository.TeamRepository;
import kr.teammangers.dev.team.repository.mapping.TeamManageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.teammangers.dev.common.payload.code.dto.enums.ErrorStatus.*;

@Service
@RequiredArgsConstructor
public class TeamManageServiceImpl implements TeamManageService {

    private final TeamManageRepository teamManageRepository;
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    @Override
    public boolean exists(Long teamId, Long memberId) {
        return teamManageRepository.existsByTeam_IdAndMember_Id(teamId, memberId);
    }

    @Override
    public Long save(Long teamId, Long memberId) {
        Team team = teamRepository.getReferenceById(teamId);
        Member member = memberRepository.getReferenceById(memberId);
        return teamManageRepository.save(TeamManageMapper.TEAM_MANAGE_MAPPER.toEntity(team, member)).getId();
    }

    @Override
    public List<TeamDto> findAllTeamDtoByMemberId(Long memberId) {
        List<TeamManage> result = teamManageRepository.findAllByMember_Id(memberId);
        return result.stream()
                .map(teamManage -> TeamMapper.TEAM_MAPPER.toDto(teamManage.getTeam()))
                .toList();
    }

    @Override
    public Long findIdByTeamIdAndMemberId(Long teamId, Long memberId) {
        return teamManageRepository.findByTeam_IdAndMember_Id(teamId, memberId)
                .orElseThrow(() -> new GeneralException(TEAM_MANAGE_NOT_FOUND)).getId();
    }

}
