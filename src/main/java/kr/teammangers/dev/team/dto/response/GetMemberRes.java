package kr.teammangers.dev.team.dto.response;

import kr.teammangers.dev.member.dto.MemberDto;
import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.team.dto.TeamMemberDto;
import lombok.Builder;

import java.util.List;

@Builder
public record GetMemberRes(
        TeamMemberDto leader,
        List<TeamMemberDto> members
) {
}
