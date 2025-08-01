package kr.teammangers.dev.data.dto.response;

import kr.teammangers.dev.data.domain.entity.Data;

public record CreateDataRes(
        Long dataId
) {
    public static CreateDataRes from(Data data) {
        return new CreateDataRes(data.getId());
    }
}
