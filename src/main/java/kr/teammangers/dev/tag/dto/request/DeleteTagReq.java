package kr.teammangers.dev.tag.dto.request;

public record DeleteTagReq(
        Long tagId,
        Long targetId
) {
}
