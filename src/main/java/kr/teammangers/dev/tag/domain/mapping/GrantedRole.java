package kr.teammangers.dev.tag.domain.mapping;

import jakarta.persistence.*;
import kr.teammangers.dev.global.common.entity.BaseField;
import kr.teammangers.dev.tag.domain.Tag;
import kr.teammangers.dev.team.domain.mapping.TeamManage;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Builder
@Table(name = "grt_role")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("use_yn <> 'N'")
@SQLDelete(sql = "UPDATE granted_role SET use_yn = 'N' WHERE id = ?")
public class GrantedRole extends BaseField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_mn_id", nullable = false)
    private TeamManage teamManage;

}
