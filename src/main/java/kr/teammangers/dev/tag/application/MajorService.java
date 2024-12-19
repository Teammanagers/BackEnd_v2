package kr.teammangers.dev.tag.application;

import kr.teammangers.dev.member.domain.entity.Member;
import kr.teammangers.dev.member.repository.MemberRepository;
import kr.teammangers.dev.tag.domain.Tag;
import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.tag.mapper.MajorMapper;
import kr.teammangers.dev.tag.repository.TagRepository;
import kr.teammangers.dev.tag.repository.mapping.MajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.teammangers.dev.tag.mapper.TagMapper.TAG_MAPPER;

@Service
@RequiredArgsConstructor
public class MajorService {

    private final MajorRepository majorRepository;
    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;

    public List<TagDto> findAllTagDtoByMemberId(Long memberId) {
        return majorRepository.findAllByMemberId(memberId).stream()
                .map(major -> TAG_MAPPER.toDto(major.getTag()))
                .toList();
    }

    public Long save(Long memberId, Long tagId) {
        Member member = memberRepository.getReferenceById(memberId);
        Tag tag = tagRepository.getReferenceById(tagId);
        return majorRepository.save(MajorMapper.MAJOR_MAPPER.toEntity(member, tag)).getId();
    }

    public void deleteAllByOptions(Long memberId, String tagName) {
        majorRepository.deleteAllByOptions(memberId, tagName);
    }
}
