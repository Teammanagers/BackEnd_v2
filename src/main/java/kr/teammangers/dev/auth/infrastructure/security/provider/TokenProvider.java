package kr.teammangers.dev.auth.infrastructure.security.provider;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import kr.teammangers.dev.auth.constant.AuthErrorMessage;
import kr.teammangers.dev.auth.domain.enums.TokenRule;
import kr.teammangers.dev.auth.domain.enums.TokenStatus;
import kr.teammangers.dev.global.error.code.ErrorStatus;
import kr.teammangers.dev.global.error.exception.GeneralException;
import kr.teammangers.dev.member.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

@Slf4j
@Component
public class TokenProvider {

    public String generateAccessToken(final Key ACCESS_SECRET, final long ACCESS_EXPIRATION, MemberDto memberDto) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(memberDto))
                .setSubject(String.valueOf(memberDto.id()))
                .setExpiration(new Date(now + ACCESS_EXPIRATION))
                .signWith(ACCESS_SECRET, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(final Key REFRESH_SECRET, final long REFRESH_EXPIRATION, final MemberDto memberDto) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setHeader(createHeader())
                .setExpiration(new Date(now + REFRESH_EXPIRATION))
                .setSubject(String.valueOf(memberDto.id()))
                .signWith(REFRESH_SECRET, SignatureAlgorithm.HS256)
                .compact();
    }

    public TokenStatus getTokenStatus(String token, Key secretKey) {
        try {
            // null 또는 빈 토큰 체크
            if (token == null || token.trim().isEmpty()) {
                log.debug("Token is null or empty");
                return TokenStatus.EXPIRED;
            }
            
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return TokenStatus.AUTHENTICATED;
        } catch (ExpiredJwtException e) {
            log.debug("Token expired: {}", e.getMessage());
            return TokenStatus.EXPIRED;
        } catch (IllegalArgumentException e) {
            log.debug("Invalid token format: {}", e.getMessage());
            return TokenStatus.EXPIRED;
        } catch (JwtException e) {
            // 500 에러 대신 401로 처리하기 위해 EXPIRED 반환
            log.debug("JWT validation failed: {}", e.getMessage());
            return TokenStatus.EXPIRED;
        } catch (Exception e) {
            // 예상치 못한 예외도 안전하게 처리
            log.error("Unexpected error during token validation: {}", e.getMessage(), e);
            return TokenStatus.EXPIRED;
        }
    }

    public String resolveTokenFromCookie(Cookie[] cookies, TokenRule tokenPrefix) {
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(tokenPrefix.getValue()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse("");
    }

    public Key getSigningKey(String secretKey) {
        String encodedKey = encodeToBase64(secretKey);
        return Keys.hmacShaKeyFor(encodedKey.getBytes(StandardCharsets.UTF_8));
    }

    public Cookie resetToken(TokenRule tokenPrefix) {
        Cookie cookie = new Cookie(tokenPrefix.getValue(), null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        return cookie;
    }

    private String encodeToBase64(String secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        return header;
    }

    private Map<String, Object> createClaims(MemberDto memberDto) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("provider", memberDto.id());
        claims.put("Role", memberDto.role());
        return claims;
    }

}
