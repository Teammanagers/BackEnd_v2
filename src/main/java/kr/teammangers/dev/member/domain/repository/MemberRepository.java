package kr.teammangers.dev.member.domain.repository;

import kr.teammangers.dev.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByProviderInfo_ProviderId(String providerId);

    // 탈퇴한 회원 포함하여 조회 (재가입 처리용)
    @Query(value = "SELECT * FROM member WHERE prov_id = :providerId", nativeQuery = true)
    Optional<Member> findByProviderIdIncludingDeleted(@Param("providerId") String providerId);

    List<Member> findAllByIdIn(List<Long> ids);
}
