package kr.teammangers.dev.member.repository;

import kr.teammangers.dev.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByProviderInfo_ProviderId(String providerId);
}
