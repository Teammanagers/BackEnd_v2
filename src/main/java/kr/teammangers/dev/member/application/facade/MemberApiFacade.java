package kr.teammangers.dev.member.application.facade;

import kr.teammangers.dev.member.application.service.MemberService;
import kr.teammangers.dev.member.dto.MemberDto;
import kr.teammangers.dev.member.dto.request.UpdateProfileReq;
import kr.teammangers.dev.member.dto.response.UpdateProfileRes;
import kr.teammangers.dev.tag.application.MajorService;
import kr.teammangers.dev.tag.application.TagService;
import kr.teammangers.dev.tag.dto.TagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static kr.teammangers.dev.member.mapper.MemberResMapper.MEMBER_RES_MAPPER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberApiFacade {

    private final MemberService memberService;
    private final MajorService majorService;
    private final TagService tagService;

    @Transactional
    public UpdateProfileRes updateProfile(Long memberId, UpdateProfileReq req) {
        MemberDto memberDto = memberService.update(memberId, req);

        List<String> existingTagNames = majorService.findAllTagDtoByMemberId(memberId).stream()
                .map(TagDto::name).toList();

        Optional.ofNullable(req.confidentRoles())
                .ifPresentOrElse(requestTagNames -> {
                    List<String> tagsToAdd = requestTagNames.stream()
                            .filter(tagName -> !existingTagNames.contains(tagName))
                            .toList();

                    List<String> tagsToRemove = existingTagNames.stream()
                            .filter(tagName -> !req.confidentRoles().contains(tagName))
                            .toList();

                    tagsToAdd.forEach(tagName -> saveMajorFromTagName(memberDto.id(), tagName));
                    tagsToRemove.forEach(tagName -> majorService.deleteAllByOptions(memberDto.id(), tagName));
                }, () -> majorService.deleteAllByOptions(memberDto.id(), null));

        return MEMBER_RES_MAPPER.toUpdateProfile(memberDto);
    }

    private void saveMajorFromTagName(Long memberId, String tagName) {
        TagDto tagDto = tagService.findDtoOrSave(tagName);
        majorService.save(memberId, tagDto.id());
    }
}
