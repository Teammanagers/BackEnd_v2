package kr.teammangers.dev.global.config;

import kr.teammangers.dev.auth.application.service.AuthService;
import kr.teammangers.dev.auth.application.service.DelegatingOAuth2UserService;
import kr.teammangers.dev.auth.application.service.TokenService;
import kr.teammangers.dev.auth.infrastructure.security.filter.TokenAuthenticationFilter;
import kr.teammangers.dev.auth.infrastructure.security.handler.OAuth2FailureHandler;
import kr.teammangers.dev.auth.infrastructure.security.handler.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;

import static kr.teammangers.dev.global.config.constant.WebConfigConstant.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthService authService;
    private final TokenService tokenService;
    private final OAuth2FailureHandler oAuth2FailureHandler;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfig(
            AuthService authService,
            TokenService tokenService,
            OAuth2FailureHandler oAuth2FailureHandler,
            OAuth2SuccessHandler oAuth2SuccessHandler,
            @Qualifier("corsConfigurationSource") CorsConfigurationSource corsConfigurationSource
    ) {
        this.authService = authService;
        this.tokenService = tokenService;
        this.oAuth2FailureHandler = oAuth2FailureHandler;
        this.oAuth2SuccessHandler = oAuth2SuccessHandler;
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_URLS).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService()))
                        .successHandler(oAuth2SuccessHandler)
                        .failureHandler(oAuth2FailureHandler)
                )
                .addFilterBefore(tokenAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
        return new DelegatingOAuth2UserService(authService);
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenService);
    }

    private static final String[] PUBLIC_URLS = {
            "/",
            Arrays.toString(PERMITTED_URI),
            Arrays.toString(WHITE_LIST_URI),
            Arrays.toString(DOCS_URI)
    };

}
