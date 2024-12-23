package kr.teammangers.dev.team.domain.entity;

import jakarta.persistence.*;
import kr.teammangers.dev.global.common.base.BaseField;
import kr.teammangers.dev.memo.domain.entity.Folder;
import kr.teammangers.dev.schedule.domain.entity.TimeSlot;
import kr.teammangers.dev.team.dto.request.UpdateTeamPasswordReq;
import kr.teammangers.dev.team.dto.request.UpdateTeamReq;
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


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "root_folder_id")
    private Folder rootFolder;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "time_slot_id", nullable = false)
    private TimeSlot timeSlot;

    public void update(UpdateTeamReq req) {
        this.title = req.title();
    }

    public void updatePassword(UpdateTeamPasswordReq req) {
        this.password = req.password();
    }
}
