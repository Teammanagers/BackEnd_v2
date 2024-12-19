package kr.teammangers.dev.member.application;

import kr.teammangers.dev.auth.infrastructure.oauth.OAuth2UserInfo;
import kr.teammangers.dev.global.error.code.ErrorStatus;
import kr.teammangers.dev.global.error.exception.GeneralException;
import kr.teammangers.dev.member.domain.entity.Member;
import kr.teammangers.dev.member.dto.MemberDto;
import kr.teammangers.dev.member.dto.req.UpdateProfileReq;
import kr.teammangers.dev.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kr.teammangers.dev.member.mapper.MemberMapper.MEMBER_MAPPER;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberDto findDtoOrSave(final OAuth2UserInfo oAuth2UserInfo) {
        return MEMBER_MAPPER.toDto(memberRepository.findByProviderInfo_ProviderId(oAuth2UserInfo.providerInfo().getProviderId())
                .orElseGet(() -> insertMember(MEMBER_MAPPER.toEntity(oAuth2UserInfo))));
    }

    public MemberDto findDtoById(final Long id) {
        return MEMBER_MAPPER.toDto(findById(id));
    }

    public MemberDto update(Long memberId, UpdateProfileReq req) {
        Member member = findById(memberId);
        member.update(req);
        return MEMBER_MAPPER.toDto(member);
    }

    private Member findById(final Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
    }

    private Member insertMember(final Member member) {
        return memberRepository.save(member);
    }

}
