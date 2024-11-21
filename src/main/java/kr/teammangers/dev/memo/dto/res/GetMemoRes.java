package kr.teammangers.dev.memo.dto.res;

import kr.teammangers.dev.tag.dto.TagDto;
import lombok.Builder;

import java.util.List;

@Builder
public record GetMemoRes(
        String title,
        String content,
        List<TagDto> memoTagList
) {
}
