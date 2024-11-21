package kr.teammangers.dev.tag.application;

import kr.teammangers.dev.tag.dto.TagDto;

import java.util.List;

public interface MemoTagService {
    Long save(Long memoId, Long tagId);

    List<TagDto> findAllTagDtoByMemoId(Long memoId);

    void deleteByMemoIdAndTagName(Long memoId, String tagName);

    void deleteAllByMemoId(Long memoId);
}
