package kr.teammangers.dev.tag.application.service;

import kr.teammangers.dev.member.domain.entity.Member;
import kr.teammangers.dev.member.domain.repository.MemberRepository;
import kr.teammangers.dev.tag.domain.entity.Tag;
import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.tag.mapper.MemberTagMapper;
import kr.teammangers.dev.tag.domain.repository.TagRepository;
import kr.teammangers.dev.tag.domain.repository.member.MemberTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.teammangers.dev.tag.mapper.TagMapper.TAG_MAPPER;

@Service
@RequiredArgsConstructor
public class MemberTagService {

    private final MemberTagRepository memberTagRepository;
    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;

    public List<TagDto> findAllTagDtoByMemberId(Long memberId) {
        return memberTagRepository.findAllByMemberId(memberId).stream()
                .map(memberTag -> TAG_MAPPER.toDto(memberTag.getTag()))
                .toList();
    }

    public Long save(Long memberId, Long tagId) {
        Member member = memberRepository.getReferenceById(memberId);
        Tag tag = tagRepository.getReferenceById(tagId);
        return memberTagRepository.save(MemberTagMapper.MEMBER_TAG_MAPPER.toEntity(member, tag)).getId();
    }

    public void deleteAllByOptions(Long memberId, String tagName) {
        memberTagRepository.deleteAllByOptions(memberId, tagName);
    }
}
