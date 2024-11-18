package kr.teammangers.dev.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Builder
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ProviderInfo {

    @Column(name = "prov_nm", nullable = false)
    private String provider;

    @Column(name = "prov_id", unique = true, nullable = false)
    private String providerId;

}
