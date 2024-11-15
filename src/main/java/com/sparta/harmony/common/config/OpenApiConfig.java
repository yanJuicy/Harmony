package com.sparta.harmony.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @package  : com.sparta.harmony.order.config
 * @name :
 * @date :
 * @author : chani0324
 * @version : 1.0.0
 */
@Configuration
public class OpenApiConfig {

    private static final String API_NAME = "Harmony";

    private static final String API_VERSION = "1.0.0";

    private static final String API_DESCRIPTION = "Harmony 레포지토리";

    @Bean
    public OpenAPI OpenAPIConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title(API_NAME)
                        .description(API_DESCRIPTION)
                        .version(API_VERSION));
    }
}
