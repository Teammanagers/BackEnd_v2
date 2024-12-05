package kr.teammangers.dev.memo.dto.res;

import lombok.Builder;

@Builder
public record FixMemoRes(
        Long memoId,
        Boolean isFixed
) {
}
