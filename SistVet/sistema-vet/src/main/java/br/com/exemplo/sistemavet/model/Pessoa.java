package br.com.exemplo.sistemavet.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pessoas")
public class Pessoa {
    @Id
    private String id;
    private String nome;
    private String cpf;
    private String telefone;
    private String endereco;
    // Para associar uma pessoa a animais, você pode armazenar uma lista de IDs de animais aqui,
    // ou lidar com a associação primariamente do lado do Animal (animal tendo um pessoaId).
    // O projeto afirma "Cada animal poderá estar associado a uma pessoa." [cite: 6]
    // e "Permitir associar uma pessoa ao animal" na API de Pessoa. [cite: 8]
    // Vamos assumir que o modelo Animal conterá 'pessoaId'. Esta API pode ter um endpoint para estabelecer esse vínculo.

    // Construtores, Getters, Setters
    public Pessoa() {}

    public Pessoa(String nome, String cpf, String telefone, String endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
}