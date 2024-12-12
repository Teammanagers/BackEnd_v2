package kr.teammangers.dev.tag.application;

import kr.teammangers.dev.memo.domain.Memo;
import kr.teammangers.dev.memo.repository.MemoRepository;
import kr.teammangers.dev.tag.domain.Tag;
import kr.teammangers.dev.tag.domain.mapping.MemoTag;
import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.tag.repository.TagRepository;
import kr.teammangers.dev.tag.repository.mapping.MemoTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.teammangers.dev.tag.mapper.MemoTagMapper.MEMO_TAG_MAPPER;
import static kr.teammangers.dev.tag.mapper.TagMapper.TAG_MAPPER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemoTagService {

    private final MemoTagRepository memoTagRepository;
    private final MemoRepository memoRepository;
    private final TagRepository tagRepository;

    public Long save(Long memoId, Long tagId) {
        Memo memo = memoRepository.getReferenceById(memoId);
        Tag tag = tagRepository.getReferenceById(tagId);
        return memoTagRepository.save(MEMO_TAG_MAPPER.toEntity(memo, tag)).getId();
    }

    public List<TagDto> findAllTagDtoByMemoId(Long memoId) {
        return findAllByMemoId(memoId).stream()
                .map(memoTag -> TAG_MAPPER.toDto(memoTag.getTag()))
                .toList();
    }

    public void deleteByMemoIdAndTagName(Long memoId, String tagName) {
        memoTagRepository.deleteByMemoIdAndTagName(memoId, tagName);
    }

    public void deleteAllByMemoId(Long memoId) {
        memoTagRepository.deleteAllByMemoId(memoId);
    }

    private List<MemoTag> findAllByMemoId(Long memoId) {
        return memoTagRepository.findAllByMemoId(memoId);
    }

}
