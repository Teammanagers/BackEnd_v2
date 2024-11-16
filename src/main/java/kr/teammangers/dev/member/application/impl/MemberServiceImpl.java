package kr.teammangers.dev.member.application.impl;

import kr.teammangers.dev.auth.dto.OAuth2UserInfo;
import kr.teammangers.dev.member.application.MemberService;
import kr.teammangers.dev.member.domain.Member;
import kr.teammangers.dev.member.dto.MemberDto;
import kr.teammangers.dev.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static kr.teammangers.dev.member.mapper.MemberMapper.MEMBER_MAPPER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public MemberDto findOneOrSave(final OAuth2UserInfo oAuth2UserInfo) {
        return findByProviderId(oAuth2UserInfo.providerInfo().getProviderId())
                .orElseGet(() -> save(MEMBER_MAPPER.toEntity(oAuth2UserInfo)));
    }

    @Override
    @Transactional
    public MemberDto save(final Member member) {
        return MEMBER_MAPPER.toDto(memberRepository.save(member));
    }

    @Override
    public Optional<MemberDto> findByProviderId(final String providerId) {
        return memberRepository.findByProviderInfo_ProviderId(providerId)
                .map(MEMBER_MAPPER::toDto);
    }

    @Override
    public MemberDto findMemberById(final Long id) {
        return memberRepository.findById(id)
                .map(MEMBER_MAPPER::toDto)
                .orElseThrow(() -> new RuntimeException(""));   // TODO: Exception
    }

}
