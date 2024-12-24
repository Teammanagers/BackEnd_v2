package kr.teammangers.dev.inquiry.mapper;

import kr.teammangers.dev.inquiry.dto.InquiryDto;
import kr.teammangers.dev.inquiry.dto.response.CreateInquiryRes;
import kr.teammangers.dev.inquiry.dto.response.DeleteInquiryRes;
import kr.teammangers.dev.inquiry.dto.response.GetInquiryRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface InquiryResMapper {

    InquiryResMapper INQUIRY_RES_MAPPER = Mappers.getMapper(InquiryResMapper.class);

    @Mapping(target = "createdInquiryId", source = "inquiryDto.id")
    CreateInquiryRes toCreate(InquiryDto inquiryDto);

    GetInquiryRes toGet(InquiryDto inquiryDto);

    @Mapping(target = "deletedInquiryId", source = "inquiryId")
    DeleteInquiryRes toDelete(Long inquiryId);
}
