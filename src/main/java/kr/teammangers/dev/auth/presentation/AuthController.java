package kr.teammangers.dev.auth.presentation;

import kr.teammangers.dev.auth.application.service.OneTimeCodeService;
import kr.teammangers.dev.auth.application.service.TokenService;
import kr.teammangers.dev.auth.dto.request.ReissueReq;
import kr.teammangers.dev.auth.dto.response.TokenRes;
import kr.teammangers.dev.auth.infrastructure.security.AuthInfo;
import kr.teammangers.dev.global.common.response.ApiRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static kr.teammangers.dev.global.error.code.SuccessStatus._LOGOUT_SUCCESS;
import static kr.teammangers.dev.global.error.code.SuccessStatus._WITHDRAW_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/auth")
public class AuthController {

    private final OneTimeCodeService oneTimeCodeService;
    private final TokenService tokenService;


    @PostMapping("/token")
    public ResponseEntity<TokenRes> issueTokenFromCode(@RequestBody Map<String, String> payload) {
        String code = payload.get("code");

        OneTimeCodeService.OneTimeCodeInfo codeInfo = oneTimeCodeService.exchangeCodeForInfo(code);
        AuthInfo authInfo = (AuthInfo) tokenService.getAuthentication(codeInfo.getAccessToken()).getPrincipal();
        TokenRes tokenResponsePart = tokenService.issueAndSaveTokens(authInfo.memberDto());

        TokenRes finalResponse = new TokenRes(
                codeInfo.isNewUser(), // 신규 사용자 여부
                tokenResponsePart.accessToken(),
                tokenResponsePart.refreshToken()
        );

        return ResponseEntity.ok(finalResponse);
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenRes> reissueTokens(@RequestBody ReissueReq reissueReq) {
        TokenRes newTokens = tokenService.reissueTokens(reissueReq.refreshToken());
        return ResponseEntity.ok(newTokens);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiRes<Void>> logout(@AuthenticationPrincipal AuthInfo authInfo) {
        tokenService.logout(authInfo.memberDto().id());
        return ResponseEntity.ok(ApiRes.onSuccess(_LOGOUT_SUCCESS, null));
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<ApiRes<Void>> withdraw(@AuthenticationPrincipal AuthInfo authInfo) {
        tokenService.withdraw(authInfo.memberDto().id());
        return ResponseEntity.ok(ApiRes.onSuccess(_WITHDRAW_SUCCESS, null));
    }
}
