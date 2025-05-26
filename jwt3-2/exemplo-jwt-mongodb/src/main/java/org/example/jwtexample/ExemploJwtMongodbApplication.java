package org.example.jwtexample; // Seu pacote base

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing; // Importar

@SpringBootApplication
@EnableMongoAuditing // Habilitar auditoria para @CreatedDate, @LastModifiedDate etc.
public class ExemploJwtMongodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExemploJwtMongodbApplication.class, args);
	}
}