package kr.teammangers.dev.tag.repository.mapping;

import kr.teammangers.dev.tag.domain.mapping.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MajorRepository extends JpaRepository<Major, Long>, MajorQueryDsl {

    @Query("SELECT m FROM Major m WHERE m.member.id = :memberId")
    List<Major> findAllByMemberId(@Param("memberId") Long memberId);

}
