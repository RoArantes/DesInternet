package org.example.jwtexample.model.mongo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "clients")
@Data
@NoArgsConstructor
public class Client {

    @Id
    private String id;

    private String nome;
    private String telefone;

    @CreatedDate
    private LocalDateTime dataCadastro;

    public Client(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }
}