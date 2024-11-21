package kr.teammangers.dev.memo.application;

import kr.teammangers.dev.memo.dto.MemoDto;
import kr.teammangers.dev.memo.dto.req.CreateMemoReq;
import kr.teammangers.dev.memo.dto.req.UpdateMemoReq;

import java.util.List;

public interface MemoService {
    MemoDto save(Long teamId, CreateMemoReq req);

    List<MemoDto> findAllDtoById(Long teamId);

    MemoDto update(UpdateMemoReq req);
}
