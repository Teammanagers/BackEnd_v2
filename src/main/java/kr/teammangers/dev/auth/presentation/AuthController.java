package kr.teammangers.dev.auth.presentation;

import jakarta.servlet.http.HttpServletResponse;
import kr.teammangers.dev.auth.application.TokenService;
import kr.teammangers.dev.auth.dto.req.TokenReq;
import kr.teammangers.dev.auth.dto.res.TokenRes;
import kr.teammangers.dev.common.payload.ApiRes;
import kr.teammangers.dev.member.application.MemberService;
import kr.teammangers.dev.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kr.teammangers.dev.auth.mapper.AuthMapper.AUTH_MAPPER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class AuthController {

    private final TokenService tokenService;
    private final MemberService memberService;

    @RequestMapping("/auth")
    public ApiRes<TokenRes> provideToken(
            HttpServletResponse response,
            @RequestBody TokenReq tokenReq
    ) {
        MemberDto memberDto = memberService.findByProviderId(tokenReq.providerId())
                .orElseThrow(() -> new RuntimeException(""));// TODO: Exception
        tokenService.validMember(memberDto);
        String accessToken = tokenService.provideAccessToken(response, memberDto);
        String refreshToken = tokenService.provideRefreshToken(response, memberDto);

        return ApiRes.onSuccess(AUTH_MAPPER.toTokenRes(accessToken, refreshToken));
    }

}
