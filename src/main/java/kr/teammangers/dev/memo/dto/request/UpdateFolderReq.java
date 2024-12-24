package kr.teammangers.dev.memo.dto.request;

public record UpdateFolderReq(
        Long folderId,
        String name
) {
}
