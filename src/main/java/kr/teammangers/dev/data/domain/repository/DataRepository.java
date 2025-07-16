package kr.teammangers.dev.data.domain.repository;

import kr.teammangers.dev.data.domain.entity.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DataRepository extends JpaRepository<Data, Long> {

    List<Data> findAllByTeamMemberId(Long teamMemberId);


}
