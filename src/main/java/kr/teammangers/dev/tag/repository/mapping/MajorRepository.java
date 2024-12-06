package kr.teammangers.dev.tag.repository.mapping;

import kr.teammangers.dev.tag.domain.mapping.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MajorRepository extends JpaRepository<Major, Long> {

    @Query("SELECT m FROM Major m WHERE m.member.id = :memberId")
    List<Major> findAllByMemberId(@Param("memberId") Long memberId);

    // TODO: QueryDSL로 통합해야 할 듯
    @Query("DELETE FROM Major m WHERE m.member.id = :memberId AND m.tag.name = :tagName")
    void deleteByMemberIdAndTagName(@Param("memberId") Long memberId, @Param("tagName") String tagName);

    @Query("DELETE FROM Major m WHERE m.member.id = :memberId")
    void deleteAllByMemberId(@Param("memberId") Long memberId);

}
