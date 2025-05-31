package br.com.exemplo.sistemavet.service;

import br.com.exemplo.sistemavet.model.Animal;
import br.com.exemplo.sistemavet.repository.AnimalRepository;
// Opcional: Importe PessoaRepository/Service se precisar validar pessoaId
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {
    @Autowired
    private AnimalRepository animalRepository;

    // Opcional:
    // @Autowired
    // private PessoaRepository pessoaRepository;

    public List<Animal> getAllAnimais() {
        return animalRepository.findAll();
    }

    public Optional<Animal> getAnimalById(String id) {
        return animalRepository.findById(id);
    }

    public List<Animal> getAnimaisByPessoaId(String pessoaId) {
        return animalRepository.findByPessoaId(pessoaId);
    }

    public List<Animal> getAnimaisByEspecie(String especie) {
        return animalRepository.findByEspecie(especie); // Exemplo para "buscar animais" [cite: 5]
    }

    public Animal createAnimal(Animal animal) {
        // Opcional: Validar se animal.getPessoaId() existe
        // if (animal.getPessoaId() != null && !pessoaRepository.existsById(animal.getPessoaId())) {
        //     throw new RuntimeException("Pessoa (proprietário) não encontrada com o id: " + animal.getPessoaId());
        // }
        return animalRepository.save(animal);
    }

    public Animal updateAnimal(String id, Animal animalDetails) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal não encontrado com o id: " + id));
        animal.setNome(animalDetails.getNome());
        animal.setEspecie(animalDetails.getEspecie());
        animal.setRaca(animalDetails.getRaca());
        animal.setIdade(animalDetails.getIdade());
        animal.setPessoaId(animalDetails.getPessoaId()); // Permitir alteração de proprietário
        return animalRepository.save(animal);
    }

    public void deleteAnimal(String id) {
        animalRepository.deleteById(id);
    }
}