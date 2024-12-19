package kr.teammangers.dev.memo.domain.repository;

import kr.teammangers.dev.memo.domain.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {

    @Query("SELECT f FROM Folder f " +
            "WHERE f.parent.id = :parentId")
    List<Folder> findAllByParentId(@Param("parentId") Long parentId);

    @Query(value = """
            WITH RECURSIVE descendants AS (
                SELECT id FROM folder WHERE id = :folderId
                UNION ALL
                SELECT f.id
                FROM folder f
                INNER JOIN descendants d ON f.parent_id = d.id
            )
            SELECT id FROM descendants
            """, nativeQuery = true)
    List<Long> findAllDescendantFolderIds(@Param("folderId") Long folderId);

    @Modifying
    @Query("DELETE FROM Folder f WHERE f.id IN :ids")
    void deleteAllByIds(@Param("ids") List<Long> ids);


}
