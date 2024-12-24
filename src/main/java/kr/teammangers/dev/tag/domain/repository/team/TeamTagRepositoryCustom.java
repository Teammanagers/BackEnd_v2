package kr.teammangers.dev.tag.domain.repository.team;

public interface TeamTagRepositoryCustom {
    void deleteAllByOptions(Long teamId, String tagName);
}
