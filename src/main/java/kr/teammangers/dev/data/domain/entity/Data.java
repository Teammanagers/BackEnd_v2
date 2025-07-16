package kr.teammangers.dev.data.domain.entity;

import jakarta.persistence.*;
import kr.teammangers.dev.global.common.base.BaseField;
import kr.teammangers.dev.team.domain.entity.TeamMember;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Builder
@Getter
@Table(name = "data")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("use_yn <> 'N'")
@SQLDelete(sql = "UPDATE data SET use_yn = 'N' WHERE id = ?")
public class Data extends BaseField {

    public Data(TeamMember teamMember) {
        this.teamMember = teamMember;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private TeamMember teamMember;

}
