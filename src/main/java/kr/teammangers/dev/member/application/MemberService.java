package kr.teammangers.dev.member.application;

import kr.teammangers.dev.auth.dto.OAuth2UserInfo;
import kr.teammangers.dev.member.domain.Member;
import kr.teammangers.dev.member.dto.MemberDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MemberService {
    MemberDto findOneOrSave(OAuth2UserInfo oAuth2UserInfo);

    @Transactional
    MemberDto save(Member member);

    Optional<MemberDto> findByProviderId(String providerId);

    MemberDto findMemberById(Long id);
}
