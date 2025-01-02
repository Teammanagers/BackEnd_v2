package kr.teammangers.dev.member.dto.request;

import java.util.List;

public record UpdateProfileReq(
        String name,
        String birth,
        String belong,
        String telNum,
        List<String> confidentRoles
) {
}
