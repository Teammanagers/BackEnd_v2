package kr.teammangers.dev.auth.application.facade;

import kr.teammangers.dev.auth.application.service.TermsService;
import kr.teammangers.dev.auth.dto.TermsDto;
import kr.teammangers.dev.global.error.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kr.teammangers.dev.global.error.code.ErrorStatus.TERMS_ALREADY_EXISTS;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthApiFacade {

    private final TermsService termsService;

    @Transactional
    public TermsDto registerTerms(Long memberId) {
        if (termsService.existsByMemberId(memberId)) throw new GeneralException(TERMS_ALREADY_EXISTS);
        return termsService.save(memberId);
    }

}
