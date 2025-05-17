package org.example;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// http://localhost:8080/swagger-ui.html (caminho padrão com a dependência)
// http://localhost:8080/api-docs (para a definição JSON)

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "[API] - Notas de Alunos JWT & MongoDB",
        description = "API para gerenciamento de usuários e notas de alunos com JWT e MongoDB", version = "1.0.0"),
        security = @SecurityRequirement(name = "bearerAuth"))
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}