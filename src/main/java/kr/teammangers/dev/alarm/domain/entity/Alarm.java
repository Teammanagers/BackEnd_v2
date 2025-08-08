package kr.teammangers.dev.alarm.domain.entity;

import jakarta.persistence.*;
import kr.teammangers.dev.alarm.domain.enums.AlarmType;
import kr.teammangers.dev.global.common.base.BaseField;
import kr.teammangers.dev.member.domain.entity.Member;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Builder
@Getter
@Table(name = "alarm")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("use_yn <> 'N'")
@SQLDelete(sql = "UPDATE alarm SET use_yn = 'N' WHERE id = ?")
public class Alarm extends BaseField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    @Column(nullable = true)
    private Long referenceId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean read;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    public boolean read() {
        read = true;
        return true;
    }

}
