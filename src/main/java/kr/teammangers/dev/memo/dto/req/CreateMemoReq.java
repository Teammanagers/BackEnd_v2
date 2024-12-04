package kr.teammangers.dev.memo.dto.req;

import java.util.List;

public record CreateMemoReq(
        Long folderId,
        String title,
        String content,
        List<String> memoTagList
) {
}
