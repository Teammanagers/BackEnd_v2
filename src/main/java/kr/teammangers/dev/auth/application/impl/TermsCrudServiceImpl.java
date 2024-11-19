package kr.teammangers.dev.auth.application.impl;

import kr.teammangers.dev.auth.application.TermsCrudService;
import kr.teammangers.dev.auth.application.TermsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TermsCrudServiceImpl implements TermsCrudService {

    private final TermsService termsService;

    @Override
    @Transactional
    public Long registerTerms(Long memberId) {
        return termsService.save(memberId);
    }

}
