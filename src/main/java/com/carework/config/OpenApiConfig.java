package com.carework.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI careworkOpenAPI() {
        final String apiKeyScheme = "ApiKey";
        return new OpenAPI()
                .info(new Info().title("Carework API").version("v1").description("API de check-ins de bem-estar"))
                .components(new Components().addSecuritySchemes(apiKeyScheme,
                        new SecurityScheme()
                                .name("X-API-KEY")
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)))
                .addSecurityItem(new SecurityRequirement().addList(apiKeyScheme));
    }
}
