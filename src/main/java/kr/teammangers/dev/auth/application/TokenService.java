package kr.teammangers.dev.auth.application;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.teammangers.dev.auth.constants.AuthConstant;
import kr.teammangers.dev.auth.dto.enums.TokenRule;
import kr.teammangers.dev.auth.dto.enums.TokenStatus;
import kr.teammangers.dev.auth.provider.TokenGenerator;
import kr.teammangers.dev.auth.util.TokenUtil;
import kr.teammangers.dev.member.dto.MemberDto;
import kr.teammangers.dev.member.dto.enums.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.security.Key;

import static kr.teammangers.dev.auth.constants.AuthConstant.*;
import static kr.teammangers.dev.auth.dto.enums.TokenRule.*;

@Slf4j
@Component
public class TokenService {

    private final CustomUserDetailsService customUserDetailsService;
    private final TokenGenerator tokenGenerator;
    private final TokenUtil tokenUtil;
//    private final TokenRepository tokenRepository;        // TODO: refresh Token 구현시 추가

    private final Key accessSecretKey;
    private final Key refreshSecretKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public TokenService(CustomUserDetailsService customUserDetailsService,
                        TokenGenerator tokenGenerator,
                        TokenUtil tokenUtil,
                        @Value("${jwt.secret-key}") String secretKey, // TODO: access, refresh 비밀키 분리 필요할 듯
                        @Value("${jwt.access.expiration}") long accessTokenExpiration,
                        @Value("${jwt.refresh.expiration}") long refreshTokenExpiration) {
        this.customUserDetailsService = customUserDetailsService;
        this.tokenGenerator = tokenGenerator;
        this.tokenUtil = tokenUtil;
        this.accessSecretKey = tokenUtil.getSigningKey(secretKey);
        this.refreshSecretKey = tokenUtil.getSigningKey(secretKey);
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public void validMember(MemberDto memberDto) {
        if (memberDto.role().equals(Role.GUEST)) {
            throw new RuntimeException("");     // TODO: Exception
        }
    }

    public String provideAccessToken(HttpServletResponse response, MemberDto memberDto) {
        String accessToken = tokenGenerator.generateAccessToken(accessSecretKey, accessTokenExpiration, memberDto);
        ResponseCookie responseCookie = setTokenToCookie(ACCESS_PREFIX.getValue(), accessToken, accessTokenExpiration / 1000);
        response.addHeader(TOKEN_ISSUE_HEADER.getValue(), responseCookie.toString());
        return accessToken;
    }

    public String provideRefreshToken(HttpServletResponse response, MemberDto memberDto) {
        String refreshToken = tokenGenerator.generateRefreshToken(refreshSecretKey, refreshTokenExpiration, memberDto);
        ResponseCookie responseCookie = setTokenToCookie(REFRESH_PREFIX.getValue(), refreshToken, refreshTokenExpiration / 1000);
        response.addHeader(TOKEN_ISSUE_HEADER.getValue(), responseCookie.toString());

//        tokenRepository.save(new Token(memberDto.id(), refreshToken));      // TODO: refreshToken 구현시 추가
        return refreshToken;
    }

    private ResponseCookie setTokenToCookie(String tokenPrefix, String token, long maxAgeSeconds) {
        return ResponseCookie.from(tokenPrefix, token)
                .path("/")
                .maxAge(maxAgeSeconds)
                .httpOnly(true)
                .sameSite("Lax")
                .secure(true)
                .build();
    }

    public boolean validateAccessToken(String token) {
        return tokenUtil.getTokenStatus(token, accessSecretKey).equals(TokenStatus.AUTHENTICATED);
    }

    public boolean validateRefreshToken(String token, String memberId) {
        boolean isRefreshValid = tokenUtil.getTokenStatus(token, refreshSecretKey).equals(TokenStatus.AUTHENTICATED);
//        Token storedToken = tokenRepository.findByMemberId(memberId)  // TODO: refreshToken 구현시 추가
//        boolean isMatchedToken = storedToken.getToken().equals(token);
        return isRefreshValid;  // && isMatchedToken
    }

    public String resolveTokenFromCookie(HttpServletRequest request, TokenRule tokenPrefix) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) throw new RuntimeException(""); // TODO: Exception
        return tokenUtil.resolveTokenFromCookie(cookies, tokenPrefix);
    }

    public String resolveTokenFromHeader(HttpServletRequest request, TokenRule tokenPrefix) {
        String token = request.getHeader(tokenPrefix.getValue());
        if(ObjectUtils.isEmpty(token)||!token.startsWith(TOKEN_PREFIX)) return null;
        return token.substring(TOKEN_PREFIX.length());
    }

    public Authentication getAuthentication(String token) {
        UserDetails principals = customUserDetailsService.loadUserByUsername(getMemberId(token, accessSecretKey));
        return new UsernamePasswordAuthenticationToken(principals, "", principals.getAuthorities());
    }

    public String getMemberId(String token, Key secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getIdFromRefresh(String refreshToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(refreshSecretKey)
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            throw new RuntimeException(""); // TODO: Exception
        }
    }

    public void logout(MemberDto memberDto, HttpServletResponse response) {
//        tokenRepository.deleteById(memberDto.id())    // TODO: refreshToken 구현시
        Cookie accessCookie = tokenUtil.resetToken(ACCESS_PREFIX);
        Cookie refreshCookie = tokenUtil.resetToken(REFRESH_PREFIX);

        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);
    }
}
