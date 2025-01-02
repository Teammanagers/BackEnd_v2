package kr.teammangers.dev.inquiry.mapper;

import kr.teammangers.dev.inquiry.dto.InquiryDto;
import kr.teammangers.dev.inquiry.dto.response.GetInquiryRes;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface InquiryResMapper {

    InquiryResMapper INQUIRY_RES_MAPPER = Mappers.getMapper(InquiryResMapper.class);

    GetInquiryRes toGet(InquiryDto inquiryDto);

}
