package kr.teammangers.dev.team.application.service;

import kr.teammangers.dev.global.error.exception.GeneralException;
import kr.teammangers.dev.member.domain.entity.Member;
import kr.teammangers.dev.member.domain.repository.MemberRepository;
import kr.teammangers.dev.member.dto.MemberDto;
import kr.teammangers.dev.schedule.domain.entity.TimeSlot;
import kr.teammangers.dev.schedule.domain.repository.TimeSlotRepository;
import kr.teammangers.dev.team.domain.entity.Team;
import kr.teammangers.dev.team.domain.entity.TeamMember;
import kr.teammangers.dev.team.domain.repository.TeamMemberRepository;
import kr.teammangers.dev.team.domain.repository.TeamRepository;
import kr.teammangers.dev.team.dto.TeamDto;
import kr.teammangers.dev.team.mapper.TeamMemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.teammangers.dev.global.error.code.ErrorStatus.TEAM_MEMBER_NOT_FOUND;
import static kr.teammangers.dev.member.mapper.MemberMapper.MEMBER_MAPPER;
import static kr.teammangers.dev.team.mapper.TeamMapper.TEAM_MAPPER;

@Service
@RequiredArgsConstructor
public class TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final TimeSlotRepository timeSlotRepository;

    public boolean exists(Long teamId, Long memberId) {
        return teamMemberRepository.existsByTeam_IdAndMember_Id(teamId, memberId);
    }

    public Long save(Long teamId, Long memberId) {
        Team team = teamRepository.getReferenceById(teamId);
        Member member = memberRepository.getReferenceById(memberId);
        TimeSlot timeSlot = timeSlotRepository.save(TimeSlot.builder().build());
        return teamMemberRepository.save(TeamMemberMapper.TEAM_MEMBER_MAPPER.toEntity(team, member, timeSlot)).getId();
    }

    public List<TeamDto> findAllTeamDtoByMemberId(Long memberId) {
        List<TeamMember> result = teamMemberRepository.findAllByMember_Id(memberId);
        return result.stream()
                .map(teamMember -> TEAM_MAPPER.toDto(teamMember.getTeam()))
                .toList();
    }

    public List<Long> findAllTeamMemberIdByTeamId(Long teamId) {
        List<TeamMember> result = teamMemberRepository.findAllByTeam_Id(teamId);
        return result.stream()
                .map(TeamMember::getId).toList();
    }

    public MemberDto findMemberDtoByTeamMemberId(Long teamMemberId) {
        return MEMBER_MAPPER.toDto(findById(teamMemberId).getMember());
    }

    public Long delete(Long teamId, Long memberId) {
        return teamMemberRepository.findByTeam_IdAndMember_Id(teamId, memberId)
                .map(teamMember -> {
                    teamMemberRepository.delete(teamMember);
                    return teamMember.getId();
                })
                .orElseThrow(() -> new GeneralException(TEAM_MEMBER_NOT_FOUND));
    }

    private TeamMember findById(Long teamMemberId) {
        return teamMemberRepository.findById(teamMemberId)
                .orElseThrow(() -> new GeneralException(TEAM_MEMBER_NOT_FOUND));
    }
}
