package kr.teammangers.dev.memo.dto.request;

import java.util.List;

public record CreateMemoReq(
        Long teamId,
        Long folderId,
        String title,
        String content,
        List<String> memoTagList
) {
}
