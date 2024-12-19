package kr.teammangers.dev.tag.application;

import kr.teammangers.dev.tag.domain.mapping.GrantedRole;
import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.tag.mapper.TagMapper;
import kr.teammangers.dev.tag.repository.mapping.GrantedRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GrantedRoleService {

    private final GrantedRoleRepository grantedRoleRepository;

    public List<TagDto> findAllTagDtoByTeamManageId(Long teamManageId) {
        return grantedRoleRepository.findAllByTeamManage_Id(teamManageId).stream()
                .map(GrantedRole::getTag)
                .map(TagMapper.TAG_MAPPER::toDto)
                .toList();
    }
}
