package kr.teammangers.dev.s3.domain.entity;

import jakarta.persistence.*;
import kr.teammangers.dev.data.domain.entity.Data;
import kr.teammangers.dev.global.common.base.BaseField;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Builder
@Table(name = "data_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("use_yn <> 'N'")
@SQLDelete(sql = "UPDATE data_file SET use_yn = 'N' WHERE id = ?")
public class DataFile extends BaseField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "data_id", nullable = false)
    private Data data;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "s3_file_id", nullable = false)
    private S3FileInfo s3FileInfo;
}
