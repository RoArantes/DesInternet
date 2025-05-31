package br.com.exemplo.sistemavet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Esta anotação é crucial!
public class SistemaGerenciamentoVetApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaGerenciamentoVetApplication.class, args);
    }

}