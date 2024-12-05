package kr.teammangers.dev.memo.application;

import kr.teammangers.dev.memo.dto.req.CreateMemoReq;
import kr.teammangers.dev.memo.dto.req.DeleteMemoReq;
import kr.teammangers.dev.memo.dto.req.FixMemoReq;
import kr.teammangers.dev.memo.dto.req.UpdateMemoReq;
import kr.teammangers.dev.memo.dto.res.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MemoCrudService {
    @Transactional
    CreateMemoRes createMemo(CreateMemoReq req);

    List<GetMemoRes> getMemoList(Long teamId);

    UpdateMemoRes updateMemo(Long memberId, UpdateMemoReq req);

    DeleteMemoRes deleteMemo(Long memberId, DeleteMemoReq req);

    FixMemoRes fixMemo(FixMemoReq req);
}
