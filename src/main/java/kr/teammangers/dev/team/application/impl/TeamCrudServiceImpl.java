package kr.teammangers.dev.team.application.impl;

import kr.teammangers.dev.s3.application.S3Service;
import kr.teammangers.dev.s3.application.TeamImgService;
import kr.teammangers.dev.s3.dto.S3FileInfoDto;
import kr.teammangers.dev.tag.application.TagService;
import kr.teammangers.dev.tag.application.TeamTagService;
import kr.teammangers.dev.tag.dto.TagDto;
import kr.teammangers.dev.team.application.TeamCrudService;
import kr.teammangers.dev.team.application.TeamMangeService;
import kr.teammangers.dev.team.application.TeamService;
import kr.teammangers.dev.team.dto.TeamDto;
import kr.teammangers.dev.team.dto.req.CreateTeamReq;
import kr.teammangers.dev.team.dto.res.CreateTeamRes;
import kr.teammangers.dev.team.dto.res.GetTeamRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static kr.teammangers.dev.s3.constant.S3Constant.TEAM_PROFILE_PATH;
import static kr.teammangers.dev.team.mapper.TeamResMapper.TEAM_RES_MAPPER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamCrudServiceImpl implements TeamCrudService {

    private final TeamService teamService;
    private final TeamMangeService teamManageService;
    private final TeamImgService teamImgService;
    private final S3Service s3Service;
    private final TagService tagService;
    private final TeamTagService teamTagService;

    @Override
    @Transactional
    public CreateTeamRes createTeam(Long authId, CreateTeamReq req, MultipartFile imageFile) {
        TeamDto teamDto = teamService.save(req);
        teamManageService.save(teamDto.id(), authId);

        if (imageFile != null) {
            S3FileInfoDto s3FileInfoDto = s3Service.uploadFile(imageFile, TEAM_PROFILE_PATH);
            teamImgService.save(teamDto.id(), s3FileInfoDto.id());
        }

        req.teamTagList().forEach(tagName -> {
            TagDto tagDto = tagService.findDtoOrSave(tagName);
            teamTagService.save(teamDto.id(), tagDto.id());
        });

        return CreateTeamRes.builder().teamId(teamDto.id()).build();
    }

    @Override
    public GetTeamRes getTeamByTeamCode(String teamCode) {
        TeamDto teamDto = teamService.findDtoByTeamCode(teamCode);

        String filePath = teamImgService.findFilePathByTeamId(teamDto.id());
        String generatedUrl = s3Service.generateUrl(filePath);

        List<TagDto> tagDtoList = teamTagService.findAllTagDtoByTeamId(teamDto.id());

        return TEAM_RES_MAPPER.toGetTeamRes(teamDto, generatedUrl, tagDtoList);
    }

}
