package kr.teammangers.dev.memo.dto.req;

import java.util.List;

public record UpdateMemoReq(
        Long memoId,
        String title,
        String content,
        List<String> memoTagList
) {
}
