package kr.teammangers.dev.memo.application;

import kr.teammangers.dev.memo.dto.req.CreateMemoReq;
import kr.teammangers.dev.memo.dto.res.CreateMemoRes;
import kr.teammangers.dev.memo.dto.res.GetMemoRes;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MemoCrudService {
    @Transactional
    CreateMemoRes createMemo(Long teamId, CreateMemoReq req);

    List<GetMemoRes> getMemoList(Long teamId);
}
