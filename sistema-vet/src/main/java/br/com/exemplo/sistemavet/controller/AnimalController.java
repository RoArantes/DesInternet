package br.com.exemplo.sistemavet.controller;

import br.com.exemplo.sistemavet.model.Animal;
import br.com.exemplo.sistemavet.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animais")
public class AnimalController {
    @Autowired
    private AnimalService animalService;

    @PostMapping
    public Animal createAnimal(@RequestBody Animal animal) {
        return animalService.createAnimal(animal);
    }

    @GetMapping
    public List<Animal> getAllAnimais(@RequestParam(required = false) String especie,
                                      @RequestParam(required = false) String pessoaId) {
        if (especie != null) {
            return animalService.getAnimaisByEspecie(especie);
        }
        if (pessoaId != null) {
            return animalService.getAnimaisByPessoaId(pessoaId);
        }
        return animalService.getAllAnimais(); // [cite: 5]
    }

    @GetMapping("/{id}")
    public ResponseEntity<Animal> getAnimalById(@PathVariable String id) {
        return animalService.getAnimalById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable String id, @RequestBody Animal animalDetails) {
        try {
            return ResponseEntity.ok(animalService.updateAnimal(id, animalDetails));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable String id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.noContent().build();
    }
}