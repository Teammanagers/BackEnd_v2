package kr.teammangers.dev.tag.application.impl;

import kr.teammangers.dev.tag.application.TagService;
import kr.teammangers.dev.tag.domain.Tag;
import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kr.teammangers.dev.tag.mapper.TagMapper.TAG_MAPPER;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public TagDto findDtoOrSave(final String tagName) {
        Tag tag = tagRepository.findByName(tagName)
                .orElseGet(() -> insert(TAG_MAPPER.toEntity(tagName)));
        return TAG_MAPPER.toDto(tag);
    }

    private Tag insert(final Tag tag) {
        return tagRepository.save(tag);
    }

}
