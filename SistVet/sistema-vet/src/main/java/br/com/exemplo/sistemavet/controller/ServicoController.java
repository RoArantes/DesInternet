package br.com.exemplo.sistemavet.controller;

import br.com.exemplo.sistemavet.model.Servico;
import br.com.exemplo.sistemavet.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {
    @Autowired
    private ServicoService servicoService;

    @PostMapping
    public ResponseEntity<?> createServico(@RequestBody Servico servico) {
        try {
            return ResponseEntity.ok(servicoService.createServico(servico)); // [cite: 10]
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<Servico> getAllServicos(@RequestParam(required = false) String animalId) {
        if (animalId != null) {
            return servicoService.getServicosByAnimalId(animalId);
        }
        return servicoService.getAllServicos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servico> getServicoById(@PathVariable String id) {
        return servicoService.getServicoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateServico(@PathVariable String id, @RequestBody Servico servicoDetails) {
        try {
            return ResponseEntity.ok(servicoService.updateServico(id, servicoDetails));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServico(@PathVariable String id) {
        servicoService.deleteServico(id);
        return ResponseEntity.noContent().build();
    }
}