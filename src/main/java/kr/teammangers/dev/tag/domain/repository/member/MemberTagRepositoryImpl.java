package kr.teammangers.dev.tag.domain.repository.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.teammangers.dev.tag.domain.entity.QMemberTag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberTagRepositoryImpl implements MemberTagRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void deleteAllByOptions(Long memberId, String tagName) {
        QMemberTag memberTag = QMemberTag.memberTag;
        queryFactory
                .delete(memberTag)
                .where(
                        memberId != null ? memberTag.member.id.eq(memberId) : null,
                        tagName != null && !tagName.isEmpty() ? memberTag.tag.name.eq(tagName) : null
                )
                .execute();
    }

}
