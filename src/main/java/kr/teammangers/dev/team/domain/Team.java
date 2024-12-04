package kr.teammangers.dev.team.domain;

import jakarta.persistence.*;
import kr.teammangers.dev.common.entity.BaseField;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Builder
@Table(name = "team")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("use_yn <> 'N'")
@SQLDelete(sql = "UPDATE team SET use_yn = 'N' WHERE id = ?")
public class Team extends BaseField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "pw")
    private String password;

    @Column(name = "code")
    private String code;

    @Column(name = "root_folder_id", nullable = false)
    private Long rootFolderId;

}
