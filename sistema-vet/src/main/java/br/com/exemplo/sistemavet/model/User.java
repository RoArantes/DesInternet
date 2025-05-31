package br.com.exemplo.sistemavet.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Set; // Se você adicionar papéis (roles)

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    // Opcional: private Set<String> roles;

    // Construtores, Getters, Setters
    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}