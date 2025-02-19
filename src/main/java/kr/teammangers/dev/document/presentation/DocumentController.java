package kr.teammangers.dev.document.presentation;

import kr.teammangers.dev.auth.infrastructure.security.AuthInfo;
import kr.teammangers.dev.document.application.facade.DocumentApiFacade;
import kr.teammangers.dev.document.dto.response.GetTeamDocumentRes;
import kr.teammangers.dev.global.common.response.ApiRes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v2/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentApiFacade documentApiFacade;

    @PostMapping("/{teamId}")
    public ApiRes<Long> postDocument(@AuthenticationPrincipal AuthInfo authInfo,
                                     @PathVariable Long teamId,
                                     @RequestPart MultipartFile file) {

        Long result = documentApiFacade.postDocument(authInfo.memberDto().id(), teamId, file);
        return ApiRes.onSuccess(result);
    }

    @GetMapping("/{teamId}")
    public ApiRes<GetTeamDocumentRes> getTeamDocument(@AuthenticationPrincipal AuthInfo authInfo,
                                        @PathVariable Long teamId) {

        GetTeamDocumentRes result = documentApiFacade.getTeamDocument(teamId);
        return ApiRes.onSuccess(result);
    }
}
