package kr.teammangers.dev.member.dto.response;

import kr.teammangers.dev.member.dto.MemberDto;
import lombok.Builder;

@Builder
public record GetMemberProfileRes(
        MemberDto memberDto,
        String imgUrl
) {
}
