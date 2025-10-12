package kr.teammangers.dev.team.dto;

import kr.teammangers.dev.member.dto.MemberDto;
import kr.teammangers.dev.tag.dto.TagDto;
import lombok.Builder;

import java.util.List;

@Builder
public record TeamMemberDto(
        Long teamMemberId,
        MemberDto member,
        String imgUrl,
        List<TagDto> grantedRoleList
) {
}
