package kr.teammangers.dev.tag.dto.request;

public record CreateTeamMemberTagReq(
        Long memberId,
        Long teamId,
        String tagName
) {
}
