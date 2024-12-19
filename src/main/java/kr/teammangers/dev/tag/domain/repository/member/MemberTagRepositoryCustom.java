package kr.teammangers.dev.tag.domain.repository.member;

public interface MemberTagRepositoryCustom {
    void deleteAllByOptions(Long memberId, String tagName);
}
