package kr.teammangers.dev.tag.dto.request;

public record UpdateTeamMemberTagReq(
        Long tagId,
        Long teamId,
        Long memberId,
        String tagName
) {
}
