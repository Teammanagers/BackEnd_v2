package kr.teammangers.dev.memo.application;

import kr.teammangers.dev.memo.dto.MemoDto;
import kr.teammangers.dev.memo.dto.req.CreateMemoReq;

public interface MemoService {
    MemoDto save(Long teamManageId, CreateMemoReq req);
}
