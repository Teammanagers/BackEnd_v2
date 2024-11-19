package kr.teammangers.dev.tag.application;

import kr.teammangers.dev.tag.dto.TagDto;

public interface TagService {
    TagDto findDtoOrSave(String tagName);
}
