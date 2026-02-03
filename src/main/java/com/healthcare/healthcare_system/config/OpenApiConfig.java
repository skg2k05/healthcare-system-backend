package com.healthcare.healthcare_system.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI healthcareOpenAPI() {

        SecurityScheme jwtScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        return new OpenAPI()
                .info(new Info()
                        .title("Healthcare Appointment System API")
                        .description("""
                                Role-based Healthcare Backend
                                
                                Roles:
                                - CITIZEN (Patient)
                                - DOCTOR
                                
                                Features:
                                - JWT Authentication
                                - Appointment booking
                                - Status updates
                                - Pagination
                                """)
                        .version("1.0.0")
                )
                .addSecurityItem(
                        new SecurityRequirement().addList("bearerAuth")
                )
                .schemaRequirement("bearerAuth", jwtScheme);
    }
}
