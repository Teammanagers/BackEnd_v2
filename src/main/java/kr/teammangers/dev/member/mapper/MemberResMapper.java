package kr.teammangers.dev.member.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemberResMapper {

    MemberResMapper MEMBER_RES_MAPPER = Mappers.getMapper(MemberResMapper.class);
}

