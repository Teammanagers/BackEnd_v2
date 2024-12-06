package kr.teammangers.dev.member.application;

import kr.teammangers.dev.auth.dto.OAuth2UserInfo;
import kr.teammangers.dev.member.dto.MemberDto;
import kr.teammangers.dev.member.dto.req.UpdateProfileReq;
import org.springframework.transaction.annotation.Transactional;

public interface MemberService {

    @Transactional
    MemberDto findDtoOrSave(OAuth2UserInfo oAuth2UserInfo);

    MemberDto findDtoByProviderId(String providerId);

    MemberDto findDtoById(Long id);

    MemberDto update(Long memberId, UpdateProfileReq req);
}
