package kr.teammangers.dev.memo.dto.res;

import kr.teammangers.dev.memo.dto.MemoDto;
import kr.teammangers.dev.tag.dto.TagDto;
import lombok.Builder;

import java.util.List;

@Builder
public record GetMemoRes(
        MemoDto memoDto,
        List<TagDto> memoTagList
) {
}
