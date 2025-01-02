package kr.teammangers.dev.memo.dto.request;

import java.util.List;

public record UpdateMemoReq(
        String title,
        String content,
        List<String> memoTagList
) {
}
