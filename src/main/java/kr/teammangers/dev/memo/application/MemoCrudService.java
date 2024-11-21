package kr.teammangers.dev.memo.application;

import kr.teammangers.dev.memo.dto.req.CreateMemoReq;
import org.springframework.transaction.annotation.Transactional;

public interface MemoCrudService {
    @Transactional
    void createMemo(Long memberId, Long teamId, CreateMemoReq req);
}
