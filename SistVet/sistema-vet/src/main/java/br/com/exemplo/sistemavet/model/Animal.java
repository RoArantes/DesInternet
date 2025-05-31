package br.com.exemplo.sistemavet.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "animais")
public class Animal {
    @Id
    private String id;
    private String nome;
    private String especie;
    private String raca;
    private int idade;
    private String pessoaId; // ID da Pessoa associada [cite: 6]

    // Construtores, Getters, Setters
    public Animal() {}

    public Animal(String nome, String especie, String raca, int idade, String pessoaId) {
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idade = idade;
        this.pessoaId = pessoaId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }
    public String getRaca() { return raca; }
    public void setRaca(String raca) { this.raca = raca; }
    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }
    public String getPessoaId() { return pessoaId; }
    public void setPessoaId(String pessoaId) { this.pessoaId = pessoaId; }
}