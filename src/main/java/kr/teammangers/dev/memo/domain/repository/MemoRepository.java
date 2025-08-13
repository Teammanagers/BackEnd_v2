package kr.teammangers.dev.memo.domain.repository;

import kr.teammangers.dev.memo.domain.entity.Memo;
import kr.teammangers.dev.team.domain.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long>, MemoRepositoryCustom {

    @Modifying
    @Query("DELETE FROM Memo m WHERE m.folder.id IN :folderIds")
    void deleteAllByFolderIds(@Param("folderIds") List<Long> folderIds);

    @Query("SELECT m FROM Memo  m WHERE m.team.id = :teamId AND m.isFixed = true")
    List<Memo> findAllByMemoListByFixed(@Param("teamId") Long teamId);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Memo m SET m.folder = null WHERE m.folder.id IN :folderIds")
    void setFolderToNullByFolderIds(@Param("folderIds") List<Long> folderIds);

    Long team(Team team);
}
