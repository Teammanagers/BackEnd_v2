package kr.teammangers.dev.memo.dto.req;

public record CreateFolderReq(
        Long parentId,
        String name
) {
}
