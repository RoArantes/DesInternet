package org.example.jwtexample.model.mongo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "products") // Nome da coleção no MongoDB
@Data
@NoArgsConstructor
public class Product {

    @Id
    private String id; // MongoDB gera automaticamente um ObjectId e o converte para String

    private String nome;
    private Float valor;

    @CreatedDate // Anotação para preencher automaticamente com a data de criação
    private LocalDateTime dataCadastro; // Usar LocalDateTime é melhor que float para datas

    public Product(String nome, Float valor) {
        this.nome = nome;
        this.valor = valor;
        // dataCadastro será preenchido pelo Spring Data MongoDB se configurado
    }
}