package kr.teammangers.dev.memo.domain;

import jakarta.persistence.*;
import kr.teammangers.dev.common.entity.BaseField;
import kr.teammangers.dev.team.domain.mapping.TeamManage;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_mn_id", nullable = false)
    private TeamManage teamManage;

}
