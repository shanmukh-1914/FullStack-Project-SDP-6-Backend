package com.mutualfund.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MutualFund Pro API")
                        .version("1.0")
                        .description("FSAD-PS06 - Investment Perception and Selection Behavior Towards Mutual Funds")
                        .contact(new Contact()
                                .name("MutualFund Pro Team")));
    }
}