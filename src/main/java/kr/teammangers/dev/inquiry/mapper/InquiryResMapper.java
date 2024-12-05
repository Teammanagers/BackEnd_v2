package kr.teammangers.dev.inquiry.mapper;

import kr.teammangers.dev.inquiry.dto.InquiryDto;
import kr.teammangers.dev.inquiry.dto.res.CreateInquiryRes;
import kr.teammangers.dev.inquiry.dto.res.DeleteInquiryRes;
import kr.teammangers.dev.inquiry.dto.res.GetInquiryRes;
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
