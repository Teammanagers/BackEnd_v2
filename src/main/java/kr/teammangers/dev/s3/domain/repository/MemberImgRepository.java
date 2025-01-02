package kr.teammangers.dev.s3.domain.repository;

import kr.teammangers.dev.s3.domain.entity.MemberImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberImgRepository extends JpaRepository<MemberImg, Long> {

    Optional<MemberImg> findByMember_Id(Long memberId);

    void deleteByMember_Id(Long memberId);
}
