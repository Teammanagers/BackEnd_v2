package kr.teammangers.dev.team.domain.mapping;

import jakarta.persistence.*;
import kr.teammangers.dev.common.entity.BaseField;
import kr.teammangers.dev.member.domain.Member;
import kr.teammangers.dev.team.domain.Team;
import lombok.*;

@Entity
@Getter
@Builder
@Table(name = "team_mn")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamManage extends BaseField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

}
