package kr.teammangers.dev.team.dto.response;

import kr.teammangers.dev.member.dto.MemberDto;
import kr.teammangers.dev.tag.dto.TagDto;
import lombok.Builder;

import java.util.List;

@Builder
public record GetMemberRes(
        Long teamMemberId,
        MemberDto member,
        String imgUrl,
        List<TagDto> grantedRoleList
) {
}
