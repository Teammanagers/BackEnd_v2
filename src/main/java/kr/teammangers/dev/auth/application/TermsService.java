package kr.teammangers.dev.auth.application;

public interface TermsService {
    Long save(Long memberId);

    boolean existsByMemberId(Long memberId);
}
