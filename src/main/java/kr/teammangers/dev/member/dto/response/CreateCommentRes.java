package kr.teammangers.dev.member.dto.response;

import lombok.Builder;

@Builder
public record CreateCommentRes(
        Long createdCommentId
) {
}
