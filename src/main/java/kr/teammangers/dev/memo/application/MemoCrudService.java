package kr.teammangers.dev.memo.application;

import kr.teammangers.dev.memo.dto.req.CreateMemoReq;
import kr.teammangers.dev.memo.dto.res.CreateMemoRes;
import org.springframework.transaction.annotation.Transactional;

public interface MemoCrudService {
    @Transactional
    CreateMemoRes createMemo(Long memberId, Long teamId, CreateMemoReq req);
}
