package kr.teammangers.dev.tag.dto.request;

public record DeleteTeamMemberTagReq(
        Long tagId,
        Long teamId,
        Long memberId
) {
}
