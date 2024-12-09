package kr.teammangers.dev.schedule.domain;

import jakarta.persistence.*;
import kr.teammangers.dev.common.entity.BaseField;
import kr.teammangers.dev.schedule.enums.DayOfWeek;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Builder
@Table(name = "time_slot")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("use_yn <> 'N'")
@SQLDelete(sql = "UPDATE time_slot SET use_yn = 'N' WHERE id = ?")
public class TimeSlot extends BaseField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_nm", nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(name = "start_tm", nullable = false)
    private String startTime;

    @Column(name = "end_tm", nullable = false)
    private String endTime;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

}
