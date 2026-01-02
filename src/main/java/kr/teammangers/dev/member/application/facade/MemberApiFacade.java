package kr.teammangers.dev.member.application.facade;

import kr.teammangers.dev.global.error.exception.GeneralException;
import kr.teammangers.dev.member.application.service.MemberService;
import kr.teammangers.dev.member.dto.MemberDto;
import kr.teammangers.dev.member.dto.response.GetMemberNameRes;
import kr.teammangers.dev.member.dto.request.UpdateProfileReq;
import kr.teammangers.dev.member.dto.response.GetMemberProfileRes;
import kr.teammangers.dev.s3.application.MemberImgService;
import kr.teammangers.dev.s3.application.S3Service;
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
    private final MemberImgService memberImgService;
    private final TagService tagService;
    private final S3Service s3Service;

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

    public GetMemberProfileRes getMemberProfile(Long memberId) {
        MemberDto memberDto = memberService.findDtoById(memberId);

        String imgUrl;
        try {
            String imgPath = memberImgService.findFilePathByMemberId(memberId);
            imgUrl = s3Service.generateUrl(imgPath);
        } catch (GeneralException e) {
            imgUrl = null;
        }

        List<TagDto> tagDtoList = memberTagService.findAllTagDtoByMemberId(memberId);

        return GetMemberProfileRes.builder()
                .memberDto(memberDto)
                .memberTagList(tagDtoList)
                .imgUrl(imgUrl)
                .build();

    }

    public GetMemberNameRes getMemberName(Long memberId) {
        MemberDto memberDto = memberService.findDtoById(memberId);
        return GetMemberNameRes.builder()
                .name(memberDto.name())
                .build();
    }

    private void saveMemberTagFromTagName(Long memberId, String tagName) {
        TagDto tagDto = tagService.findDtoOrSave(tagName, MEMBER);
        memberTagService.save(memberId, tagDto.id());
    }

}
