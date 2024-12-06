package kr.teammangers.dev.inquiry.dto.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum InquiryType {

    ACCOUNT_INQUIRY("ACCOUNT"),
    TERMS_INQUIRY("TERMS"),
    IN_SERVICE_INQUIRY("SERVICE"),
    ETC("ETC");

    @JsonCreator
    public static InquiryType fromValue(String value) {
        for (InquiryType type : InquiryType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }

    private final String value;
}
