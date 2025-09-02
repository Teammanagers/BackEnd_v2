package kr.teammangers.dev.feedback.domain.entity;

import jakarta.persistence.*;
import kr.teammangers.dev.data.domain.entity.Data;
import kr.teammangers.dev.global.common.base.BaseField;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Builder
@Table(name = "feedback")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("use_yn <> 'N'")
@SQLDelete(sql = "UPDATE feedback SET use_yn = 'N' WHERE id = ?")
public class Feedback extends BaseField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Data 엔티티와 다대일 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "data_id", nullable = false)
    private Data data;

    // 작성자 ID (Member ID)
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    // 대댓글 기능을 위한 부모 ID (최상위 댓글은 null)
    @Column(name = "parent_id")
    private Long parentId;

    public void updateContent(String content) {
        this.content = content;
    }
}
