package com.example.todayisdiary.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(
                        new Info().title("'하루의 끝' API")
                                .description("'하루의 끝' API 명세서입니다.")
                                .version("v1")
                );
    }
}
