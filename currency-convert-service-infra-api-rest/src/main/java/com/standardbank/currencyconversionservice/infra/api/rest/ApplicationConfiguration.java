package com.standardbank.currencyconversionservice.infra.api.rest;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Godwin Tavirimirwa
 * created at 23/4/2022 08:23
 *
 * <p>Configurations for the application e.g swagger, email notifications etc</p>
 */
@Configuration
public class ApplicationConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(apiInfo());
    }

    private Info apiInfo() {
        return new Info().title("Currency conversion app")
            .version("1.0")
            .description("This service is responsible for converting a given currency to another. It also shows exchange rates for different base currencies")
            .termsOfService(null)
            .license(null);
    }

}