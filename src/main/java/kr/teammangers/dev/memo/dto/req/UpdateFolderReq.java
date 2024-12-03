package kr.teammangers.dev.memo.dto.req;

public record UpdateFolderReq(
        Long folderId,
        String name
) {
}
