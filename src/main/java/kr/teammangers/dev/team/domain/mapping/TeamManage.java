package kr.teammangers.dev.team.domain.mapping;

import jakarta.persistence.*;
import kr.teammangers.dev.common.entity.BaseField;
import kr.teammangers.dev.member.domain.Member;
import kr.teammangers.dev.schedule.domain.TimeSlot;
import kr.teammangers.dev.team.domain.Team;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Builder
@Table(name = "team_mn")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("use_yn <> 'N'")
@SQLDelete(sql = "UPDATE team_mn SET use_yn = 'N' WHERE id = ?")
public class TeamManage extends BaseField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "time_slot_id", nullable = false)
    private TimeSlot timeSlot;

}
