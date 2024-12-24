package kr.teammangers.dev.tag.dto.request;

public record CreateTeamTagReq(
        Long teamId,
        String tagName
) {
}
