package com.article.hub.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("ArticleHub API")
                        .version("1.0")
                        .description("API documentation for ArticleHub Application"));
    }
}



// URL to access Swagger: http://localhost:8080/swagger-ui/index.html


