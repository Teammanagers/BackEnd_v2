package kr.teammangers.dev.member.application.service;

import kr.teammangers.dev.auth.infrastructure.oauth.OAuth2UserInfo;
import kr.teammangers.dev.global.error.code.ErrorStatus;
import kr.teammangers.dev.global.error.exception.GeneralException;
import kr.teammangers.dev.member.domain.entity.Member;
import kr.teammangers.dev.member.domain.repository.MemberRepository;
import kr.teammangers.dev.member.dto.MemberDto;
import kr.teammangers.dev.member.dto.request.UpdateProfileReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static kr.teammangers.dev.member.mapper.MemberMapper.MEMBER_MAPPER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberDto findDtoOrSave(final OAuth2UserInfo oAuth2UserInfo) {
        // 1. 먼저 활성 회원 조회
        Optional<Member> activeMember = memberRepository.findByProviderInfo_ProviderId(oAuth2UserInfo.providerInfo().getProviderId());
        if (activeMember.isPresent()) {
            return MEMBER_MAPPER.toDto(activeMember.get());
        }

        // 2. 탈퇴한 회원 포함하여 조회 (재가입 처리)
        Optional<Member> deletedMember = memberRepository.findByProviderIdIncludingDeleted(oAuth2UserInfo.providerInfo().getProviderId());
        if (deletedMember.isPresent()) {
            Member member = deletedMember.get();
            member.reactivate(); // 회원 재활성화
            return MEMBER_MAPPER.toDto(member);
        }

        // 3. 신규 회원 생성
        return MEMBER_MAPPER.toDto(insertMember(MEMBER_MAPPER.toEntity(oAuth2UserInfo)));
    }

    public MemberDto findDtoById(final Long id) {
        return MEMBER_MAPPER.toDto(findById(id));
    }

    @Transactional
    public MemberDto update(Long memberId, UpdateProfileReq req) {
        Member member = findById(memberId);
        member.update(req);
        return MEMBER_MAPPER.toDto(member);
    }

    private Member findById(final Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
    }

    public List<MemberDto> findMembersByIds(List<Long> ids) {
        return memberRepository.findAllByIdIn(ids).stream()
                .map(MEMBER_MAPPER::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = findById(memberId);
        memberRepository.delete(member);
    }

    private Member insertMember(final Member member) {
        return memberRepository.save(member);
    }

}
