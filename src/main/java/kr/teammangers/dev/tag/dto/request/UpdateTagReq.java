package kr.teammangers.dev.tag.dto.request;

public record UpdateTagReq(
        Long tagId,
        Long targetId,
        String tagName
) {
}
