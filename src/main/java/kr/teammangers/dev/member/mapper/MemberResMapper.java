package kr.teammangers.dev.member.mapper;

import kr.teammangers.dev.member.dto.MemberDto;
import kr.teammangers.dev.member.dto.res.UpdateProfileRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemberResMapper {

    MemberResMapper MEMBER_RES_MAPPER = Mappers.getMapper(MemberResMapper.class);

    @Mapping(target = "updatedMemberId", source = "memberDto.id")
    UpdateProfileRes toUpdateProfile(MemberDto memberDto);
}
