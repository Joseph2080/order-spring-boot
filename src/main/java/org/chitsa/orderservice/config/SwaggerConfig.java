package org.chitsa.orderservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${swagger.api.title}")
    private String apiTitle;

    @Value("${swagger.api.version}")
    private String apiVersion;

    @Value("${swagger.api.description}")
    private String apiDescription;

    @Value("${swagger.api.openapi-version}")
    private String openApiVersion;

    @Value("${swagger.api.security.scheme-type}")
    private String schemeType;

    @Value("${swagger.api.security.scheme}")
    private String scheme;

    @Value("${swagger.api.security.bearer-format}")
    private String bearerFormat;
    @Value("${swagger.api.security.name}")
    private String securitySchemeName;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .openapi(openApiVersion)
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.valueOf(schemeType.toUpperCase()))
                                        .scheme(scheme)
                                        .bearerFormat(bearerFormat)))
                .info(new Info()
                        .title(apiTitle)
                        .version(apiVersion)
                        .description(apiDescription));
    }
}
