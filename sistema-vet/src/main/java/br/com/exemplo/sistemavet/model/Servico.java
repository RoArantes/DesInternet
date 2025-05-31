package br.com.exemplo.sistemavet.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate; // Ou LocalDateTime

@Document(collection = "servicos")
public class Servico {
    @Id
    private String id;
    private LocalDate data;
    private String tipo;    // ex: consulta, vacinação, banho, cirurgia [cite: 9]
    private Double valor;
    private String status;  // ex: agendado, realizado, cancelado
    private String animalId; // ID do Animal atendido [cite: 10, 13]

    // Construtores, Getters, Setters
    public Servico() {}

    public Servico(LocalDate data, String tipo, Double valor, String status, String animalId) {
        this.data = data;
        this.tipo = tipo;
        this.valor = valor;
        this.status = status;
        this.animalId = animalId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getAnimalId() { return animalId; }
    public void setAnimalId(String animalId) { this.animalId = animalId; }
}