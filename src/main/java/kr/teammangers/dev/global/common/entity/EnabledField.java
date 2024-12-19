package kr.teammangers.dev.global.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import kr.teammangers.dev.global.common.enums.EntityStatus;
import lombok.Getter;

import static kr.teammangers.dev.global.common.enums.EntityStatus.Y;

@Getter
@MappedSuperclass
public abstract class EnabledField {

    @Enumerated(EnumType.STRING)
    @Column(name = "use_yn", nullable = false, length = 1)
    private EntityStatus useYn = Y;

}
