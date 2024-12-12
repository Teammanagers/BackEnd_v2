package kr.teammangers.dev.team.application;

import kr.teammangers.dev.memo.application.FolderService;
import kr.teammangers.dev.memo.dto.FolderDto;
import kr.teammangers.dev.s3.application.S3Service;
import kr.teammangers.dev.s3.application.TeamImgService;
import kr.teammangers.dev.s3.dto.S3FileInfoDto;
import kr.teammangers.dev.tag.application.TagService;
import kr.teammangers.dev.tag.application.TeamTagService;
import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.team.dto.TeamDto;
import kr.teammangers.dev.team.dto.req.CreateTeamReq;
import kr.teammangers.dev.team.dto.req.UpdateTeamReq;
import kr.teammangers.dev.team.dto.res.CreateTeamRes;
import kr.teammangers.dev.team.dto.res.GetTeamRes;
import kr.teammangers.dev.team.dto.res.UpdateTeamRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static kr.teammangers.dev.memo.constant.FolderConstant.ROOT_FOLDER;
import static kr.teammangers.dev.s3.constant.S3Constant.TEAM_PROFILE_PATH;
import static kr.teammangers.dev.team.mapper.TeamResMapper.TEAM_RES_MAPPER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamCrudService {

    private final TeamService teamService;
    private final TeamManageService teamManageService;
    private final TeamImgService teamImgService;
    private final S3Service s3Service;
    private final TagService tagService;
    private final TeamTagService teamTagService;
    private final FolderService folderService;

    @Transactional
    public CreateTeamRes createTeam(Long authId, CreateTeamReq req, MultipartFile imageFile) {
        // root 폴더 생성
        FolderDto folderDto = FolderDto.builder()
                .name(ROOT_FOLDER)
                .depth(1)
                .build();
        Long folderId = folderService.save(folderDto).id();

        TeamDto teamDto = teamService.save(req, folderId);
        teamManageService.save(teamDto.id(), authId);

        // 팀 프로필 이미지 저장
        if (imageFile != null) {
            S3FileInfoDto s3FileInfoDto = s3Service.uploadFile(imageFile, TEAM_PROFILE_PATH);
            teamImgService.save(teamDto.id(), s3FileInfoDto.id());
        }

        // Tag 생성
        req.teamTagList().forEach(tagName -> {
            TagDto tagDto = tagService.findDtoOrSave(tagName);
            teamTagService.save(teamDto.id(), tagDto.id());
        });

        return TEAM_RES_MAPPER.toCreate(teamDto);
    }

    public GetTeamRes getTeamByTeamCode(String teamCode) {
        TeamDto teamDto = teamService.findDtoByTeamCode(teamCode);
        return buildGetTeamRes(teamDto);
    }

    public List<GetTeamRes> getTeamListByMemberId(Long memberId) {
        List<TeamDto> teamDtoList = teamManageService.findAllTeamDtoByMemberId(memberId);
        return teamDtoList.stream()
                .map(this::buildGetTeamRes)
                .toList();
    }

    private GetTeamRes buildGetTeamRes(TeamDto teamDto) {
        String filePath = teamImgService.findFilePathByTeamId(teamDto.id());
        String generatedUrl = s3Service.generateUrl(filePath);

        List<TagDto> tagDtoList = teamTagService.findAllTagDtoByTeamId(teamDto.id());

        return TEAM_RES_MAPPER.toGet(teamDto, generatedUrl, tagDtoList);
    }

    @Transactional
    public UpdateTeamRes updateTeam(UpdateTeamReq req, MultipartFile imageFile) {
        TeamDto teamDto = teamService.update(req);

        // 팀 프로필 이미지 수정
        if (imageFile != null) {
            teamImgService.delete(req.teamId());        // TODO: 스케줄링으로 일정 기간마다 벌크로 제거해야 할듯
            S3FileInfoDto s3FileInfoDto = s3Service.uploadFile(imageFile, TEAM_PROFILE_PATH);
            teamImgService.save(req.teamId(), s3FileInfoDto.id());
        }

        // 태그 수정
        List<String> existingTagNames = teamTagService.findAllTagDtoByTeamId(req.teamId()).stream()
                .map(TagDto::name).toList();

        Optional.ofNullable(req.tagList())
                .ifPresentOrElse(requestTagNames -> {
                    List<String> tagsToAdd = requestTagNames.stream()
                            .filter(tagName -> !existingTagNames.contains(tagName))
                            .toList();

                    List<String> tagsToRemove = existingTagNames.stream()
                            .filter(tagName -> !req.tagList().contains(tagName))
                            .toList();

                    tagsToAdd.forEach(tagName -> saveTeamTagFromTagName(req.teamId(), tagName));
                    tagsToRemove.forEach(tagName -> teamTagService.deleteAllByOptions(req.teamId(), tagName));
                }, () -> teamTagService.deleteAllByOptions(req.teamId(), null));

        return TEAM_RES_MAPPER.toUpdate(teamDto);
    }

    private void saveTeamTagFromTagName(Long teamId, String tagName) {
        TagDto tagDto = tagService.findDtoOrSave(tagName);
        teamTagService.save(teamId, tagDto.id());
    }

}
