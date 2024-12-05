package kr.teammangers.dev.inquiry.mapper;

import kr.teammangers.dev.inquiry.domain.Inquiry;
import kr.teammangers.dev.inquiry.dto.InquiryDto;
import kr.teammangers.dev.inquiry.dto.req.CreateInquiryReq;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface InquiryMapper {

    InquiryMapper INQUIRY_MAPPER = Mappers.getMapper(InquiryMapper.class);

    @Mapping(target = "id", ignore = true)
    Inquiry toEntity(InquiryDto inquiryDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "inquiryType", source = "req.inquiryType")
    @Mapping(target = "content", source = "req.content")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "useYn", ignore = true)
    InquiryDto toDto(Long memberId, CreateInquiryReq req);

    InquiryDto toDto(Inquiry inquiry);

}
