package kr.teammangers.dev.memo.dto.response;

import kr.teammangers.dev.memo.dto.FolderDto;
import lombok.Builder;

@Builder
public record GetFolderRes(
        FolderDto folderDto
) {
}
