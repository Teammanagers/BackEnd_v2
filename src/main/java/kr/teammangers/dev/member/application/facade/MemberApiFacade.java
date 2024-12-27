package kr.teammangers.dev.member.application.facade;

import kr.teammangers.dev.member.application.service.MemberService;
import kr.teammangers.dev.member.dto.MemberDto;
import kr.teammangers.dev.member.dto.request.UpdateProfileReq;
import kr.teammangers.dev.tag.application.service.MemberTagService;
import kr.teammangers.dev.tag.application.service.TagService;
import kr.teammangers.dev.tag.dto.TagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static kr.teammangers.dev.tag.domain.enums.TagType.MEMBER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberApiFacade {

    private final MemberService memberService;
    private final MemberTagService memberTagService;
    private final TagService tagService;

    @Transactional
    public MemberDto updateProfile(Long memberId, UpdateProfileReq req) {
        MemberDto memberDto = memberService.update(memberId, req);

        List<String> existingTagNames = memberTagService.findAllTagDtoByMemberId(memberId).stream()
                .map(TagDto::name).toList();

        Optional.ofNullable(req.confidentRoles())
                .ifPresentOrElse(requestTagNames -> {
                    List<String> tagsToAdd = requestTagNames.stream()
                            .filter(tagName -> !existingTagNames.contains(tagName))
                            .toList();

                    List<String> tagsToRemove = existingTagNames.stream()
                            .filter(tagName -> !req.confidentRoles().contains(tagName))
                            .toList();

                    tagsToAdd.forEach(tagName -> saveMemberTagFromTagName(memberDto.id(), tagName));
                    tagsToRemove.forEach(tagName -> memberTagService.deleteAllByOptions(memberDto.id(), tagName));
                }, () -> memberTagService.deleteAllByOptions(memberDto.id(), null));

        return memberDto;
    }

    private void saveMemberTagFromTagName(Long memberId, String tagName) {
        TagDto tagDto = tagService.findDtoOrSave(tagName, MEMBER);
        memberTagService.save(memberId, tagDto.id());
    }
}
