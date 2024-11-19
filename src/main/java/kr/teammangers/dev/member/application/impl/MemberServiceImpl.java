package kr.teammangers.dev.member.application.impl;

import kr.teammangers.dev.auth.dto.OAuth2UserInfo;
import kr.teammangers.dev.member.application.MemberService;
import kr.teammangers.dev.member.domain.Member;
import kr.teammangers.dev.member.dto.MemberDto;
import kr.teammangers.dev.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kr.teammangers.dev.member.mapper.MemberMapper.MEMBER_MAPPER;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberDto findDtoOrSave(final OAuth2UserInfo oAuth2UserInfo) {
        return MEMBER_MAPPER.toDto(memberRepository.findByProviderInfo_ProviderId(oAuth2UserInfo.providerInfo().getProviderId())
                .orElseGet(() -> insertMember(MEMBER_MAPPER.toEntity(oAuth2UserInfo))));
    }

    @Override
    public MemberDto findDtoByProviderId(final String providerId) {
        return memberRepository.findByProviderInfo_ProviderId(providerId)
                .map(MEMBER_MAPPER::toDto)
                .orElseThrow(() -> new RuntimeException(""));   // TODO: Exception
    }

    @Override
    public MemberDto findDtoById(final Long id) {
        return MEMBER_MAPPER.toDto(findById(id));
    }

    private Member findById(final Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(""));   // TODO: Exception
    }

    private Member insertMember(final Member member) {
        return memberRepository.save(member);
    }

}
