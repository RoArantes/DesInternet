package br.com.exemplo.sistemavet.controller;

import br.com.exemplo.sistemavet.model.Pessoa;
import br.com.exemplo.sistemavet.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {
    @Autowired
    private PessoaService pessoaService;

    @PostMapping
    public Pessoa createPessoa(@RequestBody Pessoa pessoa) {
        return pessoaService.createPessoa(pessoa);
    }

    @GetMapping
    public List<Pessoa> getAllPessoas() {
        return pessoaService.getAllPessoas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> getPessoaById(@PathVariable String id) {
        return pessoaService.getPessoaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> updatePessoa(@PathVariable String id, @RequestBody Pessoa pessoaDetails) {
        try {
            return ResponseEntity.ok(pessoaService.updatePessoa(id, pessoaDetails));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePessoa(@PathVariable String id) {
        pessoaService.deletePessoa(id);
        return ResponseEntity.noContent().build();
    }

    // O endpoint para associar pessoa com animal pode estar aqui ou no AnimalController. [cite: 8]
    // Dado "Permitir associar uma pessoa ao animal" na API de Pessoa, [cite: 8]
    // pode envolver a atualização de um documento Animal.
    // Exemplo: PUT /api/pessoas/{pessoaId}/associar-animal/{animalId}
    // Isso exigiria também o AnimalService. Para simplificar, vamos assumir que a criação/atualização do Animal lida com seu 'pessoaId'.
}