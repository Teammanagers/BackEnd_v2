package kr.teammangers.dev.member.presentation;

import kr.teammangers.dev.auth.dto.AuthInfo;
import kr.teammangers.dev.common.payload.ApiRes;
import kr.teammangers.dev.member.application.MemberCrudService;
import kr.teammangers.dev.member.dto.req.UpdateProfileReq;
import kr.teammangers.dev.member.dto.res.UpdateProfileRes;
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

    private final MemberCrudService memberCrudService;

    @PostMapping("/profile")
    public ApiRes<UpdateProfileRes> updateProfile(
            @AuthenticationPrincipal final AuthInfo auth,
            @RequestBody final UpdateProfileReq req
    ) {
        UpdateProfileRes result = memberCrudService.updateProfile(auth.memberDto().id(), req);
        return ApiRes.onSuccess(result);
    }

}
