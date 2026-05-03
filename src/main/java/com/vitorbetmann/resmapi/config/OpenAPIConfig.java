package com.vitorbetmann.resmapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info().title("Resmapi - Restaurant Management API")
                                .description("REST API for restaurant management. Phase 1 covers user management (registration, updates, password change, search by name, and login validation) for restaurant owners and customers. Built for the FIAP Java Architecture and Development postgraduate program (2026).")
                                .version("v1.0.0")
                                .contact(new Contact()
                                        .name("Vitor Betmann")
                                        .url("https://github.com/vitorbetmann"))
                )
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local")
                ));
    }
}