package kr.teammangers.dev.s3.domain.repository;

import kr.teammangers.dev.s3.domain.entity.DataFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DataFileRepository extends JpaRepository<DataFile, Long> {

    Optional<DataFile> findByDataId(Long dataId);

}
