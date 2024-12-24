package kr.teammangers.dev.memo.dto.response;

import lombok.Builder;

@Builder
public record CreateFolderRes(
        Long createdFolderId
) {
}
