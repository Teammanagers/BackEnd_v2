package kr.teammangers.dev.auth.presentation;

import kr.teammangers.dev.auth.application.service.OneTimeCodeService;
import kr.teammangers.dev.auth.application.service.TokenService;
import kr.teammangers.dev.auth.dto.request.ReissueReq;
import kr.teammangers.dev.auth.dto.response.TokenRes;
import kr.teammangers.dev.auth.infrastructure.security.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/auth")
public class AuthController {

    private final OneTimeCodeService oneTimeCodeService;
    private final TokenService tokenService;


    @PostMapping("/token")
    public ResponseEntity<TokenRes> issueTokenFromCode(@RequestBody Map<String, String> payload) {
        String code = payload.get("code");

        String tempAccessToken = oneTimeCodeService.exchangeCodeForToken(code);
        AuthInfo authInfo = (AuthInfo) tokenService.getAuthentication(tempAccessToken).getPrincipal();

        TokenRes tokenResponse = tokenService.issueAndSaveTokens(authInfo.memberDto());

        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenRes> reissueTokens(@RequestBody ReissueReq reissueReq) {
        TokenRes newTokens = tokenService.reissueTokens(reissueReq.refreshToken());
        return ResponseEntity.ok(newTokens);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal AuthInfo authInfo) {
        tokenService.logout(authInfo.memberDto().id());
        return ResponseEntity.ok().build();
    }
}
