package com.authentica.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração para habilitar o botão 'Authorize' no Swagger UI.
 */
@Configuration
@OpenAPIDefinition(
    info = @Info(title = "Authentica API", version = "v1", description = "Playground para treinamento de QA"),
    security = @SecurityRequirement(name = "bearerAuth"),
    servers = {
        @io.swagger.v3.oas.annotations.servers.Server(url = "https://authenticaspringboot-production.up.railway.app", description = "Servidor de Produção (Railway)"),
        @io.swagger.v3.oas.annotations.servers.Server(url = "http://localhost:8080", description = "Servidor Local")
    }
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class OpenApiConfig {
}
