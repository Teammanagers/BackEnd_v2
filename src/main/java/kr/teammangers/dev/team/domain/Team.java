package kr.teammangers.dev.team.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@Table(name = "team")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "pw", nullable = false)
    private String password;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "img_url")
    private String imgUrl;

}
