package kr.teammangers.dev.memo.domain;

import jakarta.persistence.*;
import kr.teammangers.dev.global.common.base.BaseField;
import kr.teammangers.dev.memo.dto.req.UpdateMemoReq;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Builder
@Table(name = "memo")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("use_yn <> 'N'")
@SQLDelete(sql = "UPDATE memo SET use_yn = 'N' WHERE id = ?")
public class Memo extends BaseField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "is_fixed", nullable = false)
    private Boolean isFixed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id", nullable = false)
    private Folder folder;

    public void update(UpdateMemoReq req) {
        this.title = req.title();
        this.content = req.content();
    }

    public void updateFixStatus() {
        this.isFixed = !this.isFixed;
    }
}
