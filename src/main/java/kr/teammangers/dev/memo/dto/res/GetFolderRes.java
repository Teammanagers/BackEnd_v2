package kr.teammangers.dev.memo.dto.res;

import kr.teammangers.dev.memo.dto.FolderDto;
import lombok.Builder;

@Builder
public record GetFolderRes(
        Long currentFolderId,
        FolderDto folderDto
) {
}
