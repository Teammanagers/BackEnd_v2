package kr.teammangers.dev.tag.repository.mapping.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.teammangers.dev.tag.domain.mapping.QTeamTag;
import kr.teammangers.dev.tag.repository.mapping.TeamTagQueryDsl;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TeamTagQueryDslImpl implements TeamTagQueryDsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public void deleteAllByOptions(Long teamId, String tagName) {
        QTeamTag teamTag = QTeamTag.teamTag;
        queryFactory
                .delete(teamTag)
                .where(
                        teamId != null ? teamTag.team.id.eq(teamId) : null,
                        tagName != null && !tagName.isEmpty() ? teamTag.tag.name.eq(tagName) : null
                )
                .execute();
    }


}
