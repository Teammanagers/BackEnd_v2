package kr.teammangers.dev.s3.domain;

import jakarta.persistence.*;
import kr.teammangers.dev.global.common.entity.BaseField;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Builder
@Table(name = "s3_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("use_yn <> 'N'")
@SQLDelete(sql = "UPDATE s3_file SET use_yn = 'N' WHERE id = ?")
public class S3FileInfo extends BaseField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nm", nullable = false)
    private String originalFileName;

    @Column(name = "phys_nm", nullable = false)
    private String physicalFileName;

    @Column(name = "path", nullable = false)
    private String filePath;

    @Column(name = "ext")
    private String fileNameExtension;

    @Column(name = "size")
    private Long fileSize;

}
