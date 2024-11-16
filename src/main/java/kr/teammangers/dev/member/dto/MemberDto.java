package kr.teammangers.dev.member.dto;

import kr.teammangers.dev.auth.domain.ProviderInfo;
import kr.teammangers.dev.common.enums.EntityStatus;
import kr.teammangers.dev.member.dto.enums.Role;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MemberDto(
        Long id,
        String name,
        String birth,
        String email,
        String telNum,
        String belong,
        ProviderInfo providerInfo,
        Role role,

        // BaseField
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        EntityStatus useYn
) {
}
