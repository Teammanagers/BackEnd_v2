package kr.teammangers.dev.auth.domain.entity;

import jakarta.persistence.*;
import kr.teammangers.dev.global.common.entity.BaseField;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Builder
@Table(name = "terms")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("use_yn <> 'N'")
@SQLDelete(sql = "UPDATE terms SET use_yn = 'N' WHERE id = ?")
public class Terms extends BaseField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "use_term", nullable = false)
    private Boolean termsOfUse;

    @Column(name = "pv_policy", nullable = false)
    private Boolean privacyPolicy;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

}
