package kr.teammangers.dev.member.dto.res;

import lombok.Builder;

@Builder
public record UpdateProfileRes(
        Long updatedMemberId
) {
}
