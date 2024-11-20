package kr.teammangers.dev.team.application.base;

public interface TeamManageService {
    boolean exists(Long teamId, Long memberId);

    Long save(Long teamId, Long memberId);
}
