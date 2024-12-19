package kr.teammangers.dev.calendar.domain.entity;

import jakarta.persistence.*;
import kr.teammangers.dev.calendar.dto.request.UpdatePlanReq;
import kr.teammangers.dev.global.common.entity.BaseField;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@Table(name = "plan")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("use_yn <> 'N'")
@SQLDelete(sql = "UPDATE plan SET use_yn = 'N' WHERE id = ?")
public class Plan extends BaseField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "team_id", nullable = false)
    private Long teamId;

    public void update(UpdatePlanReq req) {
        this.title = req.title();
        this.content = req.content();
    }

}
