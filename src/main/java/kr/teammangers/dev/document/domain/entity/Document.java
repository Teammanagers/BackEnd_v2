package kr.teammangers.dev.document.domain.entity;

import jakarta.persistence.*;
import kr.teammangers.dev.s3.domain.entity.S3FileInfo;
import kr.teammangers.dev.team.domain.entity.TeamMember;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Builder
@Table(name = "document")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("use_yn <> 'N'")
@SQLDelete(sql = "UPDATE document SET use_yn = 'N' WHERE id = ?")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_member_id")
    private TeamMember teamMember;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "s3_file_id")
    private S3FileInfo s3FileInfo;

}
