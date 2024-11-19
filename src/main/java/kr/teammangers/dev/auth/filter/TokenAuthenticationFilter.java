package kr.teammangers.dev.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.teammangers.dev.auth.application.TokenService;
import kr.teammangers.dev.common.payload.code.dto.enums.ErrorStatus;
import kr.teammangers.dev.global.config.constant.WebConfigConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

import static kr.teammangers.dev.auth.dto.enums.TokenRule.ACCESS_PREFIX;
import static kr.teammangers.dev.common.payload.exception.ExceptionUtil.handleAuthException;

@Service
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
//    private final MemberService memberService;    // TODO: refreshToken 구현시


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (isPermittedUri(request.getRequestURI())) {
            SecurityContextHolder.getContext().setAuthentication(null);
            filterChain.doFilter(request, response);
            return;
        }

//        String accessToken = tokenService.resolveTokenFromCookie(request, ACCESS_PREFIX);
        String accessToken = tokenService.resolveTokenFromHeader(request, ACCESS_PREFIX);
        if (tokenService.validateAccessToken(accessToken)) {
            setAuthenticationToContext(accessToken);
            filterChain.doFilter(request, response);
            return;
        } else handleAuthException(response, ErrorStatus.AUTH_INVALID_EXPIRED_TOKEN);


//        String refreshToken = tokenService.resolveTokenFromCookie(request, REFRESH_PREFIX);
//        MemberDto memberDto = findByRefreshToken(refreshToken);     // TODO: refreshToken 구현시
//        if (jwtService.validateRefreshToken(refreshToken, memberDto.id())) {
//            String reissuedAccessToken = jwtService.provideAccessToken(response, memberDto);
//            setAuthenticationToContext(reissuedAccessToken);
//            filterChain.doFilter(request, response);
//            return;
//        }

//        jwtService.logout(memberDto, response);
    }

    private boolean isPermittedUri(String requestUri) {
        return Arrays.stream(WebConfigConstant.PERMITTED_URI)
                .anyMatch(permittedUri -> {
                    String replace = permittedUri.replace("*", "");
                    return requestUri.contains(replace) || replace.contains(requestUri);
                });
    }
    // TODO: refreshToken 구현시
//    private MemberDto findMemberByRefreshToken(String refreshToken) {
//        String id = jwtService.getIdFromRefresh(refreshToken);
//        return MEMBER_MAPPER.toDto(memberService.findMemberById(id));
//    }

    private void setAuthenticationToContext(String accessToken) {
        Authentication authentication = tokenService.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
