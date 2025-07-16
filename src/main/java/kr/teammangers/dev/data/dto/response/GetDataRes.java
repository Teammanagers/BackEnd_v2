package kr.teammangers.dev.data.dto.response;

import kr.teammangers.dev.data.dto.DataDTO;

import java.util.List;

public record GetDataRes(
        List<DataDTO> dataList
) {
    public static GetDataRes of(List<DataDTO> dataList) {
        return new GetDataRes(dataList);
    }
}
