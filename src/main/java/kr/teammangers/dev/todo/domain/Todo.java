package kr.teammangers.dev.todo.domain;

import jakarta.persistence.*;
import kr.teammangers.dev.common.entity.BaseField;
import kr.teammangers.dev.common.enums.TodoStatus;
import kr.teammangers.dev.team.domain.mapping.TeamManage;
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
    private TeamManage teamManage;

    public void updateTitle(String newTitle) {
        this.title = newTitle;
    }

    public void updateStatus(int option) {
        this.status = switch (option) {
            case 1 -> TodoStatus.IN_PROGRESS;
            case 2 -> TodoStatus.COMPLETED;
            default -> TodoStatus.PENDING;
        };
    }

}
