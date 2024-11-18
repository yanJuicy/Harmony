package com.sparta.harmony.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @package  : com.sparta.harmony.common.config
 * @name : swagger 설정
 * @date : 2024-11-17
 * @author : chani0324
 * @version : 1.0.0
 */
@Configuration
public class OpenApiConfig {

    private static final String API_NAME = "Harmony";

    private static final String API_VERSION = "1.0.0";

    private static final String API_DESCRIPTION = "Harmony 프로젝트 API";

    @Bean
    public OpenAPI OpenAPIConfig() {

        // jwt 사용 추가
        String jwt = "JWT";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
        Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
                .name(jwt)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
        );

        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title(API_NAME)
                        .description(API_DESCRIPTION)
                        .version(API_VERSION))
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
