package com.spring.jwt.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("ABHINAV BHARAT")
                        .description("ABHINAV BHARAT application")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("https://ABHINAV BHARAT.com/")))
                .externalDocs(new ExternalDocumentation()
                        .description("ABHINAV BHARAT application")
                        .url("https://ABHINAV BHARAT.com/"));
    }
}