package kr.teammangers.dev.auth.repository;

import kr.teammangers.dev.auth.domain.Terms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermsRepository extends JpaRepository<Terms, Long> {

    boolean existsByMemberId(Long memberId);

}
