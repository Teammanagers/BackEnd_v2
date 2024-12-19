package kr.teammangers.dev.tag.application.service;

import kr.teammangers.dev.tag.domain.entity.Tag;
import kr.teammangers.dev.tag.domain.enums.TagType;
import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.tag.domain.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kr.teammangers.dev.tag.mapper.TagMapper.TAG_MAPPER;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public TagDto findDtoOrSave(final String tagName, final TagType type) {
        Tag tag = tagRepository.findByName(tagName)
                .orElseGet(() -> insert(TAG_MAPPER.toEntity(tagName, type)));
        return TAG_MAPPER.toDto(tag);
    }

    private Tag insert(final Tag tag) {
        return tagRepository.save(tag);
    }

}
