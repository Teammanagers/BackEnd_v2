package kr.teammangers.dev.memo.repository;

import kr.teammangers.dev.memo.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {

    List<Memo> findAllByFolder_Id(Long folderId);

    @Modifying
    @Query("DELETE FROM Memo m WHERE m.folder.id IN :folderIds")
    void deleteAllByFolderIds(@Param("folderIds") List<Long> folderIds);

}
