package kr.teammangers.dev.team.domain.mapping;

import jakarta.persistence.*;
import kr.teammangers.dev.common.entity.BaseField;
import kr.teammangers.dev.s3.domain.S3FileInfo;
import kr.teammangers.dev.team.domain.Team;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Builder
@Table(name = "team_img")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("use_yn <> 'N'")
@SQLDelete(sql = "UPDATE team_img SET use_yn = 'N' WHERE id = ?")
public class TeamImg extends BaseField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "s3_file_id", nullable = false)
    private S3FileInfo s3FileInfo;

}
