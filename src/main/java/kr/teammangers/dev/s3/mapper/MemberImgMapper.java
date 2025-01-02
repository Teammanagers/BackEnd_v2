package kr.teammangers.dev.s3.mapper;

import kr.teammangers.dev.member.domain.entity.Member;
import kr.teammangers.dev.s3.domain.entity.S3FileInfo;
import kr.teammangers.dev.s3.domain.entity.MemberImg;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemberImgMapper {

    MemberImgMapper MEMBER_IMG_MAPPER = Mappers.getMapper(MemberImgMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "member", source = "member")
    @Mapping(target = "s3FileInfo", source = "s3FileInfo")
    MemberImg toEntity(Member member, S3FileInfo s3FileInfo);

}
