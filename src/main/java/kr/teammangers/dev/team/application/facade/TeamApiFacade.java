package kr.teammangers.dev.team.application.facade;

import kr.teammangers.dev.global.error.code.ErrorStatus;
import kr.teammangers.dev.global.error.exception.GeneralException;
import kr.teammangers.dev.member.dto.MemberDto;
import kr.teammangers.dev.memo.application.service.FolderService;
import kr.teammangers.dev.memo.dto.FolderDto;
import kr.teammangers.dev.s3.application.service.MemberImgService;
import kr.teammangers.dev.s3.application.service.S3Service;
import kr.teammangers.dev.s3.application.service.TeamImgService;
import kr.teammangers.dev.s3.dto.S3FileInfoDto;
import kr.teammangers.dev.tag.application.service.TagService;
import kr.teammangers.dev.tag.application.service.TeamMemberTagService;
import kr.teammangers.dev.tag.application.service.TeamTagService;
import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.team.application.service.TeamMemberService;
import kr.teammangers.dev.team.application.service.TeamService;
import kr.teammangers.dev.team.dto.TeamDto;
import kr.teammangers.dev.team.dto.request.CreateTeamReq;
import kr.teammangers.dev.team.dto.request.JoinTeamReq;
import kr.teammangers.dev.team.dto.request.UpdateTeamReq;
import kr.teammangers.dev.team.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static kr.teammangers.dev.memo.constant.FolderConstant.ROOT_FOLDER;
import static kr.teammangers.dev.s3.constant.S3Constant.TEAM_PROFILE_PATH;
import static kr.teammangers.dev.tag.domain.enums.TagType.TEAM;
import static kr.teammangers.dev.team.mapper.TeamResMapper.TEAM_RES_MAPPER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamApiFacade {

    private final TeamService teamService;
    private final TeamMemberService teamMemberService;
    private final TeamImgService teamImgService;
    private final S3Service s3Service;
    private final TagService tagService;
    private final TeamTagService teamTagService;
    private final FolderService folderService;
    private final TeamMemberTagService teamMemberTagService;
    private final MemberImgService memberImgService;

    @Transactional
    public CreateTeamRes createTeam(Long authId, CreateTeamReq req, MultipartFile imageFile) {
        // root 폴더 생성
        FolderDto folderDto = FolderDto.builder()
                .name(ROOT_FOLDER)
                .depth(1)
                .build();
        Long folderId = folderService.save(folderDto).id();

        TeamDto teamDto = teamService.save(req, folderId);
        teamMemberService.save(teamDto.id(), authId);

        // 팀 프로필 이미지 저장
        if (imageFile != null) {
            S3FileInfoDto s3FileInfoDto = s3Service.uploadFile(imageFile, TEAM_PROFILE_PATH);
            teamImgService.save(teamDto.id(), s3FileInfoDto.id());
        }

        // Tag 생성
        req.teamTagList().forEach(tagName -> saveTeamTagFromTagName(teamDto.id(), tagName));

        return TEAM_RES_MAPPER.toCreate(teamDto);
    }

    public GetTeamCodeRes generateTeamCode() {
        String generatedCode = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8).toUpperCase();
        return TEAM_RES_MAPPER.toGetTeamCode(generatedCode);
    }

    public GetTeamRes getTeamByTeamCode(String teamCode) {
        TeamDto teamDto = teamService.findDtoByTeamCode(teamCode);
        return buildGetTeamRes(teamDto);
    }

    public List<GetTeamRes> getTeamListByMemberId(Long memberId) {
        List<TeamDto> teamDtoList = teamMemberService.findAllTeamDtoByMemberId(memberId);
        return teamDtoList.stream()
                .map(this::buildGetTeamRes)
                .toList();
    }

    public GetTeamRes getTeamByTeamId(Long teamId) {
        TeamDto teamDto = teamService.findDtoById(teamId);
        return buildGetTeamRes(teamDto);
    }

    public List<GetMemberRes> getMemberListByTeamId(Long teamId) {
        return teamMemberService.findAllTeamMemberIdByTeamId(teamId).stream()
                .map(this::buildGetMemberRes)
                .toList();
    }

    private GetTeamRes buildGetTeamRes(TeamDto teamDto) {
        String filePath = teamImgService.findFilePathByTeamId(teamDto.id());
        String generatedUrl = s3Service.generateUrl(filePath);

        List<TagDto> tagDtoList = teamTagService.findAllTagDtoByTeamId(teamDto.id());

        return TEAM_RES_MAPPER.toGet(teamDto, generatedUrl, tagDtoList);
    }

    private GetMemberRes buildGetMemberRes(Long teamMemberId) {
        MemberDto memberDto = teamMemberService.findMemberDtoByTeamMemberId(teamMemberId);

        String filePath = memberImgService.findFilePahtByMemberId(memberDto.id());
        String generatedUrl = s3Service.generateUrl(filePath);

        List<TagDto> tagDtoList = teamMemberTagService.findAllTagDtoByTeamMemberId(teamMemberId);
        return TEAM_RES_MAPPER.toGetMember(teamMemberId, memberDto, generatedUrl, tagDtoList);
    }

    @Transactional
    public UpdateTeamRes updateTeam(UpdateTeamReq req, MultipartFile imageFile) {
        // 팀 타이틀 수정
        TeamDto teamDto = teamService.update(req);


        // 팀 프로필 이미지 수정
        if (imageFile != null) {
            teamImgService.delete(req.teamId());        // TODO: 스케줄링으로 일정 기간마다 벌크로 제거해야 할듯
            S3FileInfoDto s3FileInfoDto = s3Service.uploadFile(imageFile, TEAM_PROFILE_PATH);
            teamImgService.save(req.teamId(), s3FileInfoDto.id());
        }

//        // 태그 수정
//        List<String> existingTagNames = teamTagService.findAllTagDtoByTeamId(req.teamId()).stream()
//                .map(TagDto::name).toList();
//
//        Optional.ofNullable(req.tagList())
//                .ifPresentOrElse(requestTagNames -> {
//                    List<String> tagsToAdd = requestTagNames.stream()
//                            .filter(tagName -> !existingTagNames.contains(tagName))
//                            .toList();
//
//                    List<String> tagsToRemove = existingTagNames.stream()
//                            .filter(tagName -> !req.tagList().contains(tagName))
//                            .toList();
//
//                    tagsToAdd.forEach(tagName -> saveTeamTagFromTagName(req.teamId(), tagName));
//                    tagsToRemove.forEach(tagName -> teamTagService.deleteAllByOptions(req.teamId(), tagName));
//                }, () -> teamTagService.deleteAllByOptions(req.teamId(), null));

        return TEAM_RES_MAPPER.toUpdate(teamDto);
    }

    @Transactional
    public JoinTeamRes joinTeam(Long memberId, Long teamId, JoinTeamReq req) {
        TeamDto teamDto = teamService.findDtoById(teamId);

        if (validPassword(teamDto, req.password())) {
            throw new GeneralException(ErrorStatus.TEAM_MISMATCH_PASSWORD);
        }
        if (isAlreadyJoin(teamDto, memberId)) {
            throw new GeneralException(ErrorStatus.TEAM_ALREADY_JOIN);
        }
        Long teamMemberId = teamMemberService.save(teamId, memberId);
        return TEAM_RES_MAPPER.toJoin(teamMemberId);
    }

    private boolean validPassword(TeamDto teamDto, String password) {
        return !Objects.equals(teamDto.password(), password);
    }

    private boolean isAlreadyJoin(TeamDto teamDto, Long memberId) {
        return teamMemberService.exists(teamDto.id(), memberId);
    }

    private void saveTeamTagFromTagName(Long teamId, String tagName) {
        TagDto tagDto = tagService.findDtoOrSave(tagName, TEAM);
        teamTagService.save(teamId, tagDto.id());
    }


}
