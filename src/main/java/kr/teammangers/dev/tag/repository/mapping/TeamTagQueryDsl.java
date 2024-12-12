package kr.teammangers.dev.tag.repository.mapping;

public interface TeamTagQueryDsl {
    void deleteAllByOptions(Long teamId, String tagName);
}
