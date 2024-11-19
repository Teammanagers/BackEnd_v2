package kr.teammangers.dev.tag.application;

import kr.teammangers.dev.tag.dto.TagDto;

import java.util.List;

public interface TeamTagService {
    Long save(Long teamId, Long tagId);

    List<TagDto> findAllTagDtoByTeamId(Long teamId);
}
