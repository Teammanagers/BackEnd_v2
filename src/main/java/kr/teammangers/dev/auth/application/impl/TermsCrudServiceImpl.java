package kr.teammangers.dev.auth.application.impl;

import kr.teammangers.dev.auth.application.TermsCrudService;
import kr.teammangers.dev.auth.application.TermsService;
import kr.teammangers.dev.auth.dto.res.CreateTermsRes;
import kr.teammangers.dev.auth.mapper.TermsMapper;
import kr.teammangers.dev.common.payload.code.dto.enums.ErrorStatus;
import kr.teammangers.dev.common.payload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kr.teammangers.dev.auth.mapper.TermsMapper.*;
import static kr.teammangers.dev.common.payload.code.dto.enums.ErrorStatus.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TermsCrudServiceImpl implements TermsCrudService {

    private final TermsService termsService;

    @Override
    @Transactional
    public CreateTermsRes registerTerms(Long memberId) {
        if(termsService.existsByMemberId(memberId)) throw new GeneralException(TERMS_ALREADY_EXISTS);
        return TERMS_MAPPER.toCreateTermsRes(termsService.save(memberId));
    }

}
