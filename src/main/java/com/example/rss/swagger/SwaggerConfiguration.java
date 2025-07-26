package com.example.rss.swagger;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Rss news API")
                .version("v1")
                .description("Rss Reader")
                .contact(new Contact()
                    .name("Dinyo Dinev")
                    .email("dev.example@example.com")
                )
            );
    }
}
