package kr.teammangers.dev.memo.domain;

import jakarta.persistence.*;
import kr.teammangers.dev.global.common.base.BaseField;
import kr.teammangers.dev.memo.dto.req.UpdateFolderReq;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Builder
@Table(name = "folder")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("use_yn <> 'N'")
@SQLDelete(sql = "UPDATE folder SET use_yn = 'N' WHERE id = ?")
public class Folder extends BaseField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "depth", nullable = false)
    private Integer depth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Folder parent;

    public void update(UpdateFolderReq req) {
        this.name = req.name();
    }

}
