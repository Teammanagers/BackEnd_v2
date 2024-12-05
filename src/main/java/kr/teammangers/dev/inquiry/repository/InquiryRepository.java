package kr.teammangers.dev.inquiry.repository;

import kr.teammangers.dev.inquiry.domain.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
}
