package kr.teammangers.dev.tag.domain.repository.member;

import kr.teammangers.dev.tag.domain.entity.MemberTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberTagRepository extends JpaRepository<MemberTag, Long>, MemberTagRepositoryCustom {

    @Query("SELECT m FROM MemberTag m WHERE m.member.id = :memberId")
    List<MemberTag> findAllByMemberId(@Param("memberId") Long memberId);

}
