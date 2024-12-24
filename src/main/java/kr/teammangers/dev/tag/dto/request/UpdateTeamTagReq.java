package kr.teammangers.dev.tag.dto.request;

public record UpdateTeamTagReq(
        Long tagId,
        Long teamId,
        String tagName
) {
}
