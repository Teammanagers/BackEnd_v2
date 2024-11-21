package kr.teammangers.dev.memo.mapper;

import kr.teammangers.dev.memo.domain.Memo;
import kr.teammangers.dev.memo.dto.MemoDto;
import kr.teammangers.dev.memo.dto.req.CreateMemoReq;
import kr.teammangers.dev.team.domain.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemoMapper {

    MemoMapper MEMO_MAPPER = Mappers.getMapper(MemoMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "title", source = "req.title")
    @Mapping(target = "content", source = "req.content")
    @Mapping(target = "team", source = "team")
    Memo toEntity(CreateMemoReq req, Team team);

    @Mapping(target = "teamId", source = "team.id")
    MemoDto toDto(Memo memo);

}
