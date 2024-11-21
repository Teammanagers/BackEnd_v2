package kr.teammangers.dev.auth.application;

import kr.teammangers.dev.auth.dto.res.CreateTermsRes;

public interface TermsCrudService {
    CreateTermsRes registerTerms(Long memberId);
}
