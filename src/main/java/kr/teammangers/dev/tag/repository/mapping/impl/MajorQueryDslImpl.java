package kr.teammangers.dev.tag.repository.mapping.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.teammangers.dev.tag.domain.mapping.QMajor;
import kr.teammangers.dev.tag.repository.mapping.MajorQueryDsl;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MajorQueryDslImpl implements MajorQueryDsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public void deleteAllByOptions(Long memberId, String tagName) {
        QMajor major = QMajor.major;
        queryFactory
                .delete(major)
                .where(
                        memberId != null ? major.member.id.eq(memberId) : null,
                        tagName != null && !tagName.isEmpty() ? major.tag.name.eq(tagName) : null
                )
                .execute();
    }

}
