package kr.teammangers.dev.s3.domain.repository;

import kr.teammangers.dev.s3.domain.entity.S3FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface S3Repository extends JpaRepository<S3FileInfo, Long> {
}
