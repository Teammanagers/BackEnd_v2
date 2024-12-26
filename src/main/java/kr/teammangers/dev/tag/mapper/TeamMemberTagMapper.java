package kr.teammangers.dev.tag.mapper;

import kr.teammangers.dev.tag.domain.entity.Tag;
import kr.teammangers.dev.tag.domain.entity.TeamMemberTag;
import kr.teammangers.dev.tag.dto.TeamMemberTagDto;
import kr.teammangers.dev.team.domain.entity.TeamMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TeamMemberTagMapper {

    TeamMemberTagMapper TEAM_MEMBER_TAG_MAPPER = Mappers.getMapper(TeamMemberTagMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teamMember", source = "teamMember")
    @Mapping(target = "tag", source = "tag")
    TeamMemberTag toEntity(TeamMember teamMember, Tag tag);

    @Mapping(target = "tagId", source = "teamMemberTag.tag.id")
    @Mapping(target = "teamMemberId", source = "teamMemberTag.teamMemberTag.id")
    TeamMemberTagDto toDto(TeamMemberTag teamMemberTag);
}
