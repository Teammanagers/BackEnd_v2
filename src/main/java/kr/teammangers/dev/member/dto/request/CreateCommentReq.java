package kr.teammangers.dev.member.dto.request;

public record CreateCommentReq(
        Long memberId,
        String content
) {
}
