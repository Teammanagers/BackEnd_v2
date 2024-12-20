package kr.teammangers.dev.tag.dto.request;

public record CreateTagReq(
        Long targetId,
        String tagName
) {
}
