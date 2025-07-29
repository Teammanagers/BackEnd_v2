package kr.teammangers.dev.data.presentation;

import kr.teammangers.dev.auth.infrastructure.security.AuthInfo;
import kr.teammangers.dev.data.application.DataCrudService;
import kr.teammangers.dev.data.dto.DataDTO;
import kr.teammangers.dev.data.dto.response.GetDataRes;
import kr.teammangers.dev.global.common.response.ApiRes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v2/data")
@RequiredArgsConstructor
public class DataRestController {

    private final DataCrudService dataCrudService;

    @PostMapping("/{teamId}")
    public ApiRes<Void> uploadData(@AuthenticationPrincipal final AuthInfo authInfo,
                                   @PathVariable(name = "teamId") Long teamId,
                                   @RequestPart(name = "file") MultipartFile file) {

        dataCrudService.createData(authInfo.memberDto().id(), teamId, file);
        return ApiRes.onSuccess();
    }

    @GetMapping("/{teamId}")
    public ApiRes<GetDataRes> getTeamData(@PathVariable(name = "teamId") Long teamId) {

        GetDataRes result = dataCrudService.getTeamData(teamId);

        return ApiRes.onSuccess(result);
    }

    @DeleteMapping("/{dataId}")
    public ApiRes<Void> deleteData(@PathVariable(name = "dataId") Long dataId) {

        dataCrudService.deleteData(dataId);

        return ApiRes.onSuccess();
    }
}
