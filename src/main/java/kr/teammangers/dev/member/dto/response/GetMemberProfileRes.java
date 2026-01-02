package kr.teammangers.dev.member.dto.response;

import kr.teammangers.dev.member.dto.MemberDto;
import kr.teammangers.dev.tag.dto.TagDto;
import lombok.Builder;

import java.util.List;

@Builder
public record GetMemberProfileRes(
        MemberDto memberDto,
        List<TagDto> memberTagList,
        String imgUrl
) {
}
