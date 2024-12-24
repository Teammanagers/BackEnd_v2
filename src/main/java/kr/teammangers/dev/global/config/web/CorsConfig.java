package kr.teammangers.dev.global.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static kr.teammangers.dev.global.common.constant.WebConfigConstant.*;

@Configuration
public class CorsConfig {

    @Bean("corsConfigurationSource")
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(CORS_ALLOW_ORIGIN));
        configuration.setAllowedMethods(Arrays.asList(CORS_ALLOW_METHODS));
        configuration.setAllowedHeaders(Arrays.asList(CORS_ALLOW_HEADERS));
        configuration.setExposedHeaders(Arrays.asList(CORS_EXPOSE_HEADERS));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3000L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
