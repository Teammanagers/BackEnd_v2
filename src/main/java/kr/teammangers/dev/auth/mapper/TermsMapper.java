package kr.teammangers.dev.auth.mapper;

import kr.teammangers.dev.auth.domain.Terms;
import kr.teammangers.dev.auth.dto.res.CreateTermsRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TermsMapper {

    TermsMapper TERMS_MAPPER = Mappers.getMapper(TermsMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "termsOfUse", constant = "true")
    @Mapping(target = "privacyPolicy", constant = "true")
    @Mapping(target = "memberId", source = "memberId")
    Terms toEntity(Long memberId);

    @Mapping(target = "createdTermsId", source = "termsId")
    CreateTermsRes toCreateTermsRes(Long termsId);

}
