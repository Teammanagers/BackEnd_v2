package kr.teammangers.dev.memo.dto.request;

public record CreateFolderReq(
        Long parentId,
        String name
) {
}
