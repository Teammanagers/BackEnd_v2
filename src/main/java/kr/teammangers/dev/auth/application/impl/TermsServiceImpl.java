package kr.teammangers.dev.auth.application.impl;

import kr.teammangers.dev.auth.application.TermsService;
import kr.teammangers.dev.auth.domain.Terms;
import kr.teammangers.dev.auth.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kr.teammangers.dev.auth.mapper.TermsMapper.TERMS_MAPPER;

@Service
@RequiredArgsConstructor
public class TermsServiceImpl implements TermsService {

    private final TermsRepository termsRepository;

    @Override
    public Long save(Long memberId) {
        return insert(memberId).getMemberId();
    }

    @Override
    public boolean existsByMemberId(Long memberId) {
        return termsRepository.existsByMemberId(memberId);
    }

    private Terms insert(Long memberId) {
        return termsRepository.save(TERMS_MAPPER.toEntity(memberId));
    }

}
