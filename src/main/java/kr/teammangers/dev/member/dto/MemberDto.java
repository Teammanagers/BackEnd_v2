package kr.teammangers.dev.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.teammangers.dev.member.domain.embed.ProviderInfo;
import kr.teammangers.dev.global.common.enums.EntityStatus;
import kr.teammangers.dev.member.enums.Role;
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
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime createdAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime updatedAt,
        EntityStatus useYn
) {
}
