package kr.teammangers.dev.tag.domain.repository.team;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.teammangers.dev.tag.domain.entity.QTeamTag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TeamTagRepositoryImpl implements TeamTagRepositoryCustom {

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
