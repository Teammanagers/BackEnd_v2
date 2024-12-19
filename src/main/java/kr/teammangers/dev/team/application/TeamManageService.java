package kr.teammangers.dev.team.application;

import kr.teammangers.dev.global.error.exception.GeneralException;
import kr.teammangers.dev.member.domain.entity.Member;
import kr.teammangers.dev.member.dto.MemberDto;
import kr.teammangers.dev.member.domain.repository.MemberRepository;
import kr.teammangers.dev.schedule.domain.TimeSlot;
import kr.teammangers.dev.schedule.repository.TimeSlotRepository;
import kr.teammangers.dev.team.domain.Team;
import kr.teammangers.dev.team.domain.mapping.TeamManage;
import kr.teammangers.dev.team.dto.TeamDto;
import kr.teammangers.dev.team.mapper.TeamManageMapper;
import kr.teammangers.dev.team.repository.TeamRepository;
import kr.teammangers.dev.team.repository.mapping.TeamManageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.teammangers.dev.global.error.code.ErrorStatus.TEAM_MANAGE_NOT_FOUND;
import static kr.teammangers.dev.member.mapper.MemberMapper.MEMBER_MAPPER;
import static kr.teammangers.dev.team.mapper.TeamMapper.TEAM_MAPPER;

@Service
@RequiredArgsConstructor
public class TeamManageService {

    private final TeamManageRepository teamManageRepository;
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final TimeSlotRepository timeSlotRepository;

    public boolean exists(Long teamId, Long memberId) {
        return teamManageRepository.existsByTeam_IdAndMember_Id(teamId, memberId);
    }

    public Long save(Long teamId, Long memberId) {
        Team team = teamRepository.getReferenceById(teamId);
        Member member = memberRepository.getReferenceById(memberId);
        TimeSlot timeSlot = timeSlotRepository.save(TimeSlot.builder().build());
        return teamManageRepository.save(TeamManageMapper.TEAM_MANAGE_MAPPER.toEntity(team, member, timeSlot)).getId();
    }

    public List<TeamDto> findAllTeamDtoByMemberId(Long memberId) {
        List<TeamManage> result = teamManageRepository.findAllByMember_Id(memberId);
        return result.stream()
                .map(teamManage -> TEAM_MAPPER.toDto(teamManage.getTeam()))
                .toList();
    }

    public List<Long> findAllTeamManageIdByTeamId(Long teamId) {
        List<TeamManage> result = teamManageRepository.findAllByTeam_Id(teamId);
        return result.stream()
                .map(TeamManage::getId).toList();
    }

    public List<MemberDto> findAllMemberDtoByTeamId(Long teamId) {
        List<TeamManage> result = teamManageRepository.findAllByTeam_Id(teamId);
        return result.stream()
                .map(teamManage -> MEMBER_MAPPER.toDto(teamManage.getMember()))
                .toList();
    }

    public MemberDto findMemberDtoByTeamManageId(Long teamManageId) {
        return MEMBER_MAPPER.toDto(findById(teamManageId).getMember());
    }

    private TeamManage findById(Long teamManageId) {
        return teamManageRepository.findById(teamManageId)
                .orElseThrow(() -> new GeneralException(TEAM_MANAGE_NOT_FOUND));
    }
}
