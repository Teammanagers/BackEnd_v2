package kr.teammangers.dev.auth.provider;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.teammangers.dev.member.dto.MemberDto;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenGenerator {

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
                .signWith(REFRESH_SECRET, SignatureAlgorithm.HS256)
                .compact();
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
