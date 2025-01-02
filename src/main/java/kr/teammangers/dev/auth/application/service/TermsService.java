package kr.teammangers.dev.auth.application.service;

import kr.teammangers.dev.auth.domain.entity.Terms;
import kr.teammangers.dev.auth.domain.repository.TermsRepository;
import kr.teammangers.dev.auth.dto.TermsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kr.teammangers.dev.auth.mapper.TermsMapper.TERMS_MAPPER;

@Service
@RequiredArgsConstructor
public class TermsService {

    private final TermsRepository termsRepository;

    public TermsDto save(Long memberId) {
        return TERMS_MAPPER.toDto(insert(memberId));
    }

    public boolean existsByMemberId(Long memberId) {
        return termsRepository.existsByMemberId(memberId);
    }

    private Terms insert(Long memberId) {
        return termsRepository.save(TERMS_MAPPER.toEntity(memberId));
    }

}
