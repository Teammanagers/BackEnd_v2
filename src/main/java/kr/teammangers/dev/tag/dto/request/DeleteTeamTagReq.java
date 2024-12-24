package kr.teammangers.dev.tag.dto.request;

public record DeleteTeamTagReq(
        Long tagId,
        Long teamId
) {
}
