package kr.teammangers.dev.global.common.constant;

import org.springframework.beans.factory.annotation.Value;

public final class WebConfigConstant {

    private WebConfigConstant() {
    }

    public static final String[] PERMITTED_URI = {
            "/login",
            "/oauth2/**"
    };

    public static final String[] WHITE_LIST_URI ={
            "/health"
    };

    public static final String[] DOCS_URI = {
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
    };

    public static final String[] PERMITTED_ROLE = {
            "USER",
            "ADMIN"
    };

    public static final String[] CORS_ALLOW_ORIGIN = {
            "http://localhost:5173",
            "http://localhost:8080"
    };

    public static final String[] CORS_ALLOW_METHODS = {
            "GET",
            "POST",
            "PUT",
            "PATCH",
            "DELETE",
            "OPTIONS"
    };

    public static final String[] CORS_ALLOW_HEADERS = {
            "*"
    };

    public static final String[] CORS_EXPOSE_HEADERS = {
            "Authorization",
            "Authorization-refresh"
    };

    @Value("${url.be}")
    public String serverUrl;

    @Value("${url.fe}")
    public String clientUrl;

}
