package kr.teammangers.dev.member.domain;

import jakarta.persistence.*;
import kr.teammangers.dev.auth.domain.embed.ProviderInfo;
import kr.teammangers.dev.common.entity.BaseField;
import kr.teammangers.dev.member.enums.Role;
import kr.teammangers.dev.member.dto.req.UpdateProfileReq;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Builder
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("use_yn <> 'N'")
@SQLDelete(sql = "UPDATE member SET use_yn = 'N' WHERE id = ?")
public class Member extends BaseField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birth")
    private String birth;

    @Column(name = "email")
    private String email;

    @Column(name = "tel_num")
    private String telNum;

    @Column(name = "belong")
    private String belong;

    @Embedded
    private ProviderInfo providerInfo;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role = Role.GUEST;

    public void update(UpdateProfileReq req) {
        this.name = req.name();
        this.birth = req.birth();
        this.telNum = req.telNum();
        this.belong = req.belong();
    }
}
