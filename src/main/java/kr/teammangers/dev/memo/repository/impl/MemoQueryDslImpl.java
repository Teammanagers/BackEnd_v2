package kr.teammangers.dev.memo.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.teammangers.dev.memo.domain.Memo;
import kr.teammangers.dev.memo.domain.QMemo;
import kr.teammangers.dev.memo.repository.MemoQueryDsl;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MemoQueryDslImpl implements MemoQueryDsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Memo> findAllByOptions(Long folderId, Boolean isFixed) {
        QMemo memo = QMemo.memo;
        return queryFactory
                .selectFrom(memo)
                .where(
                        memo.folder.id.eq(folderId),
                        isFixed != null ? memo.isFixed.eq(isFixed) : null
                )
                .fetch();
    }

}
