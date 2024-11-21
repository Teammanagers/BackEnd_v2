package kr.teammangers.dev.memo.application;

import kr.teammangers.dev.memo.dto.req.CreateMemoReq;
import kr.teammangers.dev.memo.dto.req.DeleteMemoReq;
import kr.teammangers.dev.memo.dto.req.UpdateMemoReq;
import kr.teammangers.dev.memo.dto.res.CreateMemoRes;
import kr.teammangers.dev.memo.dto.res.DeleteMemoRes;
import kr.teammangers.dev.memo.dto.res.GetMemoRes;
import kr.teammangers.dev.memo.dto.res.UpdateMemoRes;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MemoCrudService {
    @Transactional
    CreateMemoRes createMemo(CreateMemoReq req);

    List<GetMemoRes> getMemoList(Long teamId);

    UpdateMemoRes updateMemo(Long memberId, UpdateMemoReq req);

    DeleteMemoRes deleteMemo(Long memberId, DeleteMemoReq req);
}
