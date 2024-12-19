package kr.teammangers.dev.auth.application.facade;

import kr.teammangers.dev.auth.application.service.TermsService;
import kr.teammangers.dev.auth.dto.response.CreateTermsRes;
import kr.teammangers.dev.global.common.payload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kr.teammangers.dev.auth.mapper.TermsMapper.TERMS_MAPPER;
import static kr.teammangers.dev.global.common.payload.code.dto.enums.ErrorStatus.TERMS_ALREADY_EXISTS;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthApiFacade {

    private final TermsService termsService;

    @Transactional
    public CreateTermsRes registerTerms(Long memberId) {
        if (termsService.existsByMemberId(memberId)) throw new GeneralException(TERMS_ALREADY_EXISTS);
        return TERMS_MAPPER.toCreateTermsRes(termsService.save(memberId));
    }

}
