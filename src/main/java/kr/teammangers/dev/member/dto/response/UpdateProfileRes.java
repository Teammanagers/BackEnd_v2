package kr.teammangers.dev.member.dto.response;

import lombok.Builder;

@Builder
public record UpdateProfileRes(
        Long updatedMemberId
) {
}
