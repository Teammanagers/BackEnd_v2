package kr.teammangers.dev.inquiry.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InquiryType {

    ACCOUNT_INQUIRY("ACCOUNT"),
    TERMS_INQUIRY("TERMS"),
    IN_SERVICE_INQUIRY("SERVICE"),
    ETC("ETC");

    private final String value;
}
