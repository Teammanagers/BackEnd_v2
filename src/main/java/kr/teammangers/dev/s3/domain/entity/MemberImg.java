package kr.teammangers.dev.s3.domain.entity;

import jakarta.persistence.*;
import kr.teammangers.dev.global.common.base.BaseField;
import kr.teammangers.dev.member.domain.entity.Member;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Builder
@Table(name = "member_img")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("use_yn <> 'N'")
@SQLDelete(sql = "UPDATE member_img SET use_yn = 'N' WHERE id = ?")
public class MemberImg extends BaseField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "s3_file_id", nullable = false)
    private S3FileInfo s3FileInfo;

}
