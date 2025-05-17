package org.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios") // Define o nome da coleção no MongoDB
public class Usuario {

    @Id
    private String id; // MongoDB usa String para IDs por padrão

    @Indexed(unique = true) // Garante que o email seja único
    private String email;
    private String senha; // Em uma aplicação real, armazene senhas com hash (ex: BCrypt)

    public Usuario() {
    }

    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}