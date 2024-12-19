package kr.teammangers.dev.inquiry.domain.repository;

import kr.teammangers.dev.inquiry.domain.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    List<Inquiry> findAllByMemberId(Long memberId);
}
