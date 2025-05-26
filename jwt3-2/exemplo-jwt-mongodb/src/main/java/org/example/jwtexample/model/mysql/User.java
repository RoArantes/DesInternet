package org.example.jwtexample.model.mysql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users") // Nome da tabela no MySQL
@Data // Lombok: gera getters, setters, toString, equals, hashCode
@NoArgsConstructor // Lombok: gera construtor sem argumentos
@AllArgsConstructor // Lombok: gera construtor com todos os argumentos
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID auto-incrementado pelo MySQL
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    // Poderíamos adicionar roles (funções/perfis) aqui se necessário
    // private String roles; // Ex: "ROLE_USER,ROLE_ADMIN"
}