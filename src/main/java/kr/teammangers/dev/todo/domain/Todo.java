package kr.teammangers.dev.todo.domain;

import jakarta.persistence.*;
import kr.teammangers.dev.global.common.base.BaseField;
import kr.teammangers.dev.global.common.enums.TodoStatus;
import kr.teammangers.dev.global.error.code.ErrorStatus;
import kr.teammangers.dev.global.error.exception.GeneralException;
import kr.teammangers.dev.team.domain.entity.TeamMember;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Builder
@Getter
@Table(name = "todo")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("use_yn <> 'N'")
@SQLDelete(sql = "UPDATE team SET use_yn = 'N' WHERE id = ?")
public class Todo extends BaseField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TodoStatus status = TodoStatus.PENDING;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private TeamMember teamMember;

    public void updateTitle(String newTitle) {
        this.title = newTitle;
    }

    public void updateStatus(int option) {
        this.status = switch (option) {
            case 0 -> TodoStatus.PENDING;
            case 1 -> TodoStatus.IN_PROGRESS;
            case 2 -> TodoStatus.COMPLETED;
            default -> throw new GeneralException(ErrorStatus.OPTION_OUT_OF_RANGE);
        };
    }

}
