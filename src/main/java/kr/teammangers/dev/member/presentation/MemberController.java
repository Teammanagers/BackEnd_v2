package kr.teammangers.dev.member.presentation;

import kr.teammangers.dev.auth.infrastructure.security.AuthInfo;
import kr.teammangers.dev.global.common.response.ApiRes;
import kr.teammangers.dev.member.application.facade.MemberApiFacade;
import kr.teammangers.dev.member.dto.MemberDto;
import kr.teammangers.dev.member.dto.request.UpdateProfileReq;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/member")
public class MemberController {

    private final MemberApiFacade memberApiFacade;

    @PostMapping
    public ApiRes<MemberDto> updateProfile(
            @AuthenticationPrincipal final AuthInfo auth,
            @RequestBody final UpdateProfileReq req
    ) {
        MemberDto result = memberApiFacade.updateProfile(auth.memberDto().id(), req);
        return ApiRes.onSuccess(result);
    }

}
