package org.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notas_alunos")
public class NotaAluno {

    @Id
    private String id;
    private String nome; // Nome do aluno
    private float nota1;
    private float nota2;
    private float media;
    private String emailUsuario; // Email do usuário que cadastrou/possui esta nota

    public NotaAluno() {
    }

    public NotaAluno(String nome, float nota1, float nota2, String emailUsuario) {
        this.nome = nome;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.emailUsuario = emailUsuario;
        this.calcularMedia(); // Calcula a média na criação
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getNota1() {
        return nota1;
    }

    public void setNota1(float nota1) {
        this.nota1 = nota1;
        calcularMedia(); // Recalcula a média se a nota1 mudar
    }

    public float getNota2() {
        return nota2;
    }

    public void setNota2(float nota2) {
        this.nota2 = nota2;
        calcularMedia(); // Recalcula a média se a nota2 mudar
    }

    public float getMedia() {
        return media;
    }

    // A média é calculada, então não há um setter direto para ela
    // public void setMedia(float media) { this.media = media; }


    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    // Método para calcular a média
    public void calcularMedia() {
        this.media = (this.nota1 + this.nota2) / 2;
    }
}