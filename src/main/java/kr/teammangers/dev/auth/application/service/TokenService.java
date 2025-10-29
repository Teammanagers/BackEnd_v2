package kr.teammangers.dev.auth.application.service;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import kr.teammangers.dev.auth.domain.enums.TokenRule;
import kr.teammangers.dev.auth.domain.enums.TokenStatus;
import kr.teammangers.dev.auth.dto.response.TokenRes;
import kr.teammangers.dev.auth.infrastructure.security.AuthInfo;
import kr.teammangers.dev.auth.infrastructure.security.provider.TokenProvider;
import kr.teammangers.dev.member.dto.MemberDto;
import kr.teammangers.dev.member.domain.enums.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.security.Key;
import java.time.Duration;

import static kr.teammangers.dev.auth.constant.AuthConstant.TOKEN_PREFIX;

@Slf4j
@Service
@Transactional(readOnly = true)
public class TokenService {

    private final AuthService authService;
    private final TokenProvider tokenProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private final kr.teammangers.dev.member.application.service.MemberService memberService;

    private final Key accessSecretKey;
    private final Key refreshSecretKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public TokenService(AuthService authService,
                        TokenProvider tokenProvider,
                        RedisTemplate<String, String> redisTemplate,
                        kr.teammangers.dev.member.application.service.MemberService memberService,
                        @Value("${jwt.secret-key}") String secretKey,
                        @Value("${jwt.secret-key-refresh}") String refreshSecretKeyString,
                        @Value("${jwt.access.expiration}") long accessTokenExpiration,
                        @Value("${jwt.refresh.expiration}") long refreshTokenExpiration) {
        this.authService = authService;
        this.tokenProvider = tokenProvider;
        this.redisTemplate = redisTemplate;
        this.memberService = memberService;
        this.accessSecretKey = tokenProvider.getSigningKey(secretKey);
        this.refreshSecretKey = tokenProvider.getSigningKey(refreshSecretKeyString);
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public String generateNewAccessToken(MemberDto memberDto) {
        validMember(memberDto);
        return tokenProvider.generateAccessToken(accessSecretKey, accessTokenExpiration, memberDto);
    }

    @Transactional
    public TokenRes issueAndSaveTokens(MemberDto memberDto) {
        validMember(memberDto);
        String accessToken = tokenProvider.generateAccessToken(accessSecretKey, accessTokenExpiration, memberDto);
        String refreshToken = tokenProvider.generateRefreshToken(refreshSecretKey, refreshTokenExpiration, memberDto);
        redisTemplate.opsForValue().set(
                String.valueOf(memberDto.id()),
                refreshToken,
                Duration.ofMillis(refreshTokenExpiration)
        );
        return new TokenRes(false,accessToken, refreshToken);
    }

    @Transactional
    public TokenRes reissueTokens(String refreshToken) {
        if (!validateRefreshToken(refreshToken)) {
            throw new RuntimeException("유효하지 않은 Refresh Token 입니다.");
        }
        String memberId = getMemberId(refreshToken, refreshSecretKey);
        String storedRefreshToken = redisTemplate.opsForValue().get(memberId);
        if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
            throw new RuntimeException("저장된 토큰과 일치하지 않습니다. 재로그인이 필요합니다.");
        }

        UserDetails userDetails = authService.loadUserByUsername(memberId);
        AuthInfo authInfo = (AuthInfo) userDetails;
        return issueAndSaveTokens(authInfo.memberDto());
    }

    @Transactional
    public void logout(Long memberId) {
        redisTemplate.delete(String.valueOf(memberId));
        log.info("로그아웃 처리 완료. Member ID: {}", memberId);
    }

    @Transactional
    public void withdraw(Long memberId) {
        // 1. Redis에서 Refresh Token 삭제 (로그아웃 처리)
        redisTemplate.delete(String.valueOf(memberId));
        
        // 2. 회원 정보 소프트 삭제 (Member 엔티티의 @SQLDelete 어노테이션 활용)
        memberService.deleteMember(memberId);
        
        log.info("회원탈퇴 처리 완료. Member ID: {}", memberId);
    }

    public Authentication getAuthentication(String accessToken) {
        String memberId = getMemberId(accessToken, accessSecretKey);
        UserDetails userDetails = authService.loadUserByUsername(memberId);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public void validMember(MemberDto memberDto) {
        if (memberDto.role().equals(Role.GUEST)) {
            throw new RuntimeException("GUEST 등급은 토큰을 발급받을 수 없습니다.");
        }
    }

    public boolean validateAccessToken(String token) {
        return tokenProvider.getTokenStatus(token, accessSecretKey).equals(TokenStatus.AUTHENTICATED);
    }

    public boolean validateRefreshToken(String token) {
        return tokenProvider.getTokenStatus(token, refreshSecretKey).equals(TokenStatus.AUTHENTICATED);
    }

    public String resolveTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader(TokenRule.ACCESS_PREFIX.getValue());
        if (ObjectUtils.isEmpty(token) || !token.startsWith(TOKEN_PREFIX)) return null;
        return token.substring(TOKEN_PREFIX.length());
    }

    public String getMemberId(String token, Key secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
